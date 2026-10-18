package queuecheck;

import java.lang.invoke.*;
import java.lang.reflect.*;
import java.nio.file.*;
import java.util.*;
import javax.tools.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.io.*;

/** Предоставляет доступ к открытому API GenericQueue для проверки операций очереди. */
public final class QueueAccess {
    public final Class<?> type;
    public final Class<? extends Throwable> full, empty;
    private final MethodHandle put, get;
    private Map<Class<?>,MethodHandle> constructors;
    /** Отдельный тип элемента для проверки параметризованной очереди. */
    public record Payload(int value) {}
    private QueueAccess(Class<?> type, Class<? extends Throwable> full,
                        Class<? extends Throwable> empty) throws ReflectiveOperationException {
        this.type=type;this.full=full;this.empty=empty;
        var lookup=MethodHandles.publicLookup();
        put=lookup.findVirtual(type,"put",MethodType.methodType(void.class,Object.class));
        get=lookup.findVirtual(type,"get",MethodType.methodType(Object.class));
    }
    public Object create(Object[] storage) throws Throwable {
        if(constructors==null)throw new IllegalStateException("Вспомогательный класс для вызова конструкторов не подготовлен");
        MethodHandle constructor=constructors.get(storage.getClass());
        if(constructor==null)throw new IllegalArgumentException("Неизвестный тип массива в проверке: "+storage.getClass());
        return constructor.invoke((Object)storage);
    }
    public void put(Object queue,Object value) throws Throwable {put.invoke(queue,value);}
    public Object get(Object queue) throws Throwable {return get.invoke(queue);}

    private static boolean visible(Class<?> type) {
        for(Class<?> part=type;part!=null;part=part.getEnclosingClass())
            if(!Modifier.isPublic(part.getModifiers()))return false;
        return type.getCanonicalName()!=null;
    }
    private static boolean checked(Class<?> t) {
        return Throwable.class.isAssignableFrom(t) && !RuntimeException.class.isAssignableFrom(t)
            && !Error.class.isAssignableFrom(t);
    }
    private static Class<? extends Throwable> failure(Class<?> receiver,Method method,String simpleName) {
        var checked=new ArrayList<Class<?>>();
        Type[] generic=method.getGenericExceptionTypes();Class<?>[] erased=method.getExceptionTypes();
        for(int i=0;i<generic.length;i++) {
            if(!checked(erased[i]))continue;
            Type resolved=generic(receiver,method,generic[i]);
            if(!(resolved instanceof Class<?> type))return null;
            if(checked(type))checked.add(type);
        }
        if(checked.size()!=1)return null;
        Class<?> type=checked.getFirst();
        if(!type.getSimpleName().equals(simpleName)||!visible(type)||!Exception.class.isAssignableFrom(type))return null;
        return type.asSubclass(Throwable.class);
    }
    private static Type replace(Type t,Map<TypeVariable<?>,Type> env) {
        while(t instanceof TypeVariable<?> variable && env.containsKey(variable) && env.get(variable)!=t)t=env.get(variable);
        return t;
    }
    private static Type resolve(Class<?> current,Class<?> owner,Type wanted,Map<TypeVariable<?>,Type> env) {
        if(current==owner)return replace(wanted,env);
        var parents=new ArrayList<Type>();if(current.getGenericSuperclass()!=null)parents.add(current.getGenericSuperclass());
        parents.addAll(Arrays.asList(current.getGenericInterfaces()));
        for(Type parent:parents){
            Class<?> raw=parent instanceof ParameterizedType pt?(Class<?>)pt.getRawType():(Class<?>)parent;
            if(!owner.isAssignableFrom(raw))continue;
            var next=new HashMap<TypeVariable<?>,Type>();
            if(parent instanceof ParameterizedType pt){
                TypeVariable<?>[] names=raw.getTypeParameters();Type[] values=pt.getActualTypeArguments();
                for(int i=0;i<names.length;i++)next.put(names[i],replace(values[i],env));
            }
            Type answer=resolve(raw,owner,wanted,next);if(answer!=null)return answer;
        }
        return null;
    }
    private static Type generic(Class<?> type,Method method,Type wanted) {
        var env=new HashMap<TypeVariable<?>,Type>();for(var t:type.getTypeParameters())env.put(t,t);
        return resolve(type,method.getDeclaringClass(),wanted,env);
    }
    private static Method declaration(Class<?> owner,Method method) {
        if(!method.isBridge())return method;
        var parents=new ArrayList<Class<?>>();if(owner.getSuperclass()!=null)parents.add(owner.getSuperclass());
        parents.addAll(Arrays.asList(owner.getInterfaces()));
        for(Class<?> parent:parents){
            try {
                Method candidate=parent.getMethod(method.getName(),method.getParameterTypes());
                if(Modifier.isPublic(candidate.getModifiers()))return declaration(parent,candidate);
            } catch(NoSuchMethodException ignored){}
        }
        return method;
    }
    private static boolean storageComponent(Type component,TypeVariable<?> target,
                                            Constructor<?> constructor,Set<Type> visited) {
        if(target.equals(component))return true;
        if(!(component instanceof TypeVariable<?> variable)
                ||!variable.getGenericDeclaration().equals(constructor)||!visited.add(variable))return false;
        return Arrays.stream(variable.getBounds()).anyMatch(bound->storageComponent(bound,target,constructor,visited));
    }
    /** Сопоставляет параметры типов, конструктор, методы и исключения с договором очереди. */
    public static QueueAccess shape(Class<?> type) {
        try {
            if(!visible(type)||type.isInterface()||Modifier.isAbstract(type.getModifiers())||type.getTypeParameters().length!=1)return null;
            TypeVariable<?> t=type.getTypeParameters()[0];if(!Arrays.equals(t.getBounds(),new Type[]{Object.class}))return null;
            Constructor<?> constructor=type.getConstructor(Object[].class);
            Type parameter=constructor.getGenericParameterTypes()[0];
            if(!(parameter instanceof GenericArrayType array)
                    ||!storageComponent(array.getGenericComponentType(),t,constructor,new HashSet<>())
                    ||Arrays.stream(constructor.getExceptionTypes()).anyMatch(QueueAccess::checked))return null;
            Method put=declaration(type,type.getMethod("put",Object.class)),get=declaration(type,type.getMethod("get"));
            if(Modifier.isStatic(put.getModifiers())||Modifier.isStatic(get.getModifiers())||put.getReturnType()!=void.class
                    ||!t.equals(generic(type,put,put.getGenericParameterTypes()[0]))
                    ||!t.equals(generic(type,get,get.getGenericReturnType())))return null;
            var full=failure(type,put,"QueueFullException");var empty=failure(type,get,"QueueEmptyException");
            return full==null||empty==null?null:new QueueAccess(type,full,empty);
        } catch(ReflectiveOperationException|LinkageError|TypeNotPresentException|MalformedParameterizedTypeException e){return null;}
    }
    private static QueueAccess selected;
    public static synchronized QueueAccess select() throws Exception {
        if(selected!=null)return selected;
        var candidates=new TreeMap<String,Class<?>>();
        for(String item:System.getProperty("java.class.path").split(java.io.File.pathSeparator)){
            Path root=Path.of(item);if(!Files.isDirectory(root)||Files.isSymbolicLink(root))continue;
            try(var stream=Files.walk(root)){
                for(Path path:stream.filter(p->Files.isRegularFile(p,LinkOption.NOFOLLOW_LINKS)).toList()){
                    String leaf=path.getFileName().toString();
                    if(!leaf.equals("GenericQueue.class")&&!leaf.endsWith("$GenericQueue.class"))continue;
                    String binary=root.relativize(path).toString().replace(java.io.File.separatorChar,'.').replaceFirst("\\.class$","");
                    Class<?> type=Class.forName(binary,false,QueueAccess.class.getClassLoader());
                    if(type.getSimpleName().equals("GenericQueue"))candidates.put(binary,type);
                }
            }
        }
        var matching=new ArrayList<QueueAccess>();
        for(Class<?> type:candidates.values()) {
            QueueAccess candidate=shape(type);
            if(candidate!=null&&typedCompatible(candidate))matching.add(candidate);
        }
        if(matching.size()!=1)throw new IllegalStateException("Не удалось однозначно выбрать GenericQueue; число подходящих классов: "+matching.size());
        QueueAccess candidate=matching.getFirst();
        // Подготовка вызовов конструкторов для массивов с разными типами компонентов.
        candidate.prepareConstructors();
        selected=candidate;return selected;
    }
    private static String constructorBridgeName() throws ClassNotFoundException {
        for(int suffix=0;;suffix++) {
            String name="QueueConstructorBridge"+suffix;
            try {Class.forName(name,false,QueueAccess.class.getClassLoader());}
            catch(ClassNotFoundException free){return name;}
        }
    }
    /** Компилирует вспомогательный класс: javac выбирает конструктор для каждого типа массива. */
    private void prepareConstructors() throws Exception {
        JavaCompiler compiler=ToolProvider.getSystemJavaCompiler();
        if(compiler==null)throw new IllegalStateException("Для подготовки вызовов конструкторов нужен JDK 25");
        var studentSource=type.getProtectionDomain().getCodeSource();
        var helperSource=QueueAccess.class.getProtectionDomain().getCodeSource();
        if(studentSource==null||helperSource==null)throw new IllegalStateException("Не удалось определить расположение классов очереди или средства проверки");
        Path student=Path.of(studentSource.getLocation().toURI()),helper=Path.of(helperSource.getLocation().toURI());
        if(!Files.isDirectory(student)||!Files.isDirectory(helper))throw new IllegalStateException("Для компиляции вспомогательного класса требуются каталоги классов очереди и средства проверки");
        String name=constructorBridgeName(),queue=type.getCanonicalName();
        StringBuilder source=new StringBuilder("public final class "+name+" {");
        Map<String,String> elements=new LinkedHashMap<>();
        elements.put("strings","java.lang.String");elements.put("integers","java.lang.Integer");
        elements.put("numbers","java.lang.Number");elements.put("payloads","queuecheck.QueueAccess.Payload");
        for(var entry:elements.entrySet()) {
            String element=entry.getValue();
            source.append("public static Object ").append(entry.getKey()).append('(').append(element)
                .append("[] storage) throws Throwable {").append(queue).append('<').append(element)
                .append("> result=new ").append(queue).append("<>(storage);return result;}");
        }
        source.append('}');
        JavaFileObject input=new SimpleJavaFileObject(URI.create("string:///"+name+".java"),JavaFileObject.Kind.SOURCE) {
            @Override public CharSequence getCharContent(boolean ignored){return source;}
        };
        var diagnostics=new DiagnosticCollector<JavaFileObject>();
        var outputs=new HashMap<String,ByteArrayOutputStream>();
        int[] total={0};
        try(StandardJavaFileManager standard=compiler.getStandardFileManager(diagnostics,Locale.ROOT,StandardCharsets.UTF_8);
            var memory=new ForwardingJavaFileManager<StandardJavaFileManager>(standard) {
                @Override public JavaFileObject getJavaFileForOutput(Location location,String binary,JavaFileObject.Kind kind,FileObject sibling) {
                    var output=new ByteArrayOutputStream() {
                        private void reserve(int count){if(count>1048576-total[0])throw new IllegalStateException("Превышен допустимый объём байткода вспомогательного класса");total[0]+=count;}
                        @Override public synchronized void write(int value){reserve(1);super.write(value);}
                        @Override public synchronized void write(byte[] bytes,int offset,int length){reserve(length);super.write(bytes,offset,length);}
                    };
                    if(outputs.putIfAbsent(binary,output)!=null)throw new IllegalStateException("Компилятор повторно выдал байткод вспомогательного класса");
                    return new SimpleJavaFileObject(URI.create("memory:///"+binary.replace('.','/')+kind.extension),kind) {
                        @Override public OutputStream openOutputStream(){return output;}
                    };
                }
            }) {
            String classpath=helper+File.pathSeparator+student;
            boolean success=compiler.getTask(null,memory,diagnostics,List.of("--release","25","-encoding","UTF-8","-proc:none","-Xmaxerrs","5","-sourcepath","","-classpath",classpath),null,List.of(input)).call();
            if(!success){String message=diagnostics.getDiagnostics().toString();throw new IllegalStateException("Не удалось скомпилировать вспомогательный класс для вызова конструкторов: "+message.substring(0,Math.min(1000,message.length())));}
        }
        if(outputs.size()!=1||!outputs.containsKey(name))throw new IllegalStateException("Получен неожиданный набор скомпилированных вспомогательных классов");
        byte[] bytes=outputs.get(name).toByteArray();
        // В безымянном пакете вспомогательный класс определяется тем же
        // загрузчиком, что и очередь.
        Class<?> factory=type.getPackageName().isEmpty()
            ?MethodHandles.privateLookupIn(type,MethodHandles.lookup()).defineClass(bytes)
            :new ClassLoader(QueueAccess.class.getClassLoader()) {
            Class<?> define(){return defineClass(name,bytes,0,bytes.length);}
        }.define(); // Класс определяется напрямую из полученного байткода.
        var lookup=MethodHandles.publicLookup();
        constructors=Map.of(
            String[].class,lookup.findStatic(factory,"strings",MethodType.methodType(Object.class,String[].class)),
            Integer[].class,lookup.findStatic(factory,"integers",MethodType.methodType(Object.class,Integer[].class)),
            Number[].class,lookup.findStatic(factory,"numbers",MethodType.methodType(Object.class,Number[].class)),
            Payload[].class,lookup.findStatic(factory,"payloads",MethodType.methodType(Object.class,Payload[].class)));
    }
    public record Client(String name,String source,boolean expected) {}
    public static List<Client> clients(QueueAccess q) {
        var clients=new ArrayList<Client>();
        String type=q.type.getCanonicalName(),full=q.full.getCanonicalName(),empty=q.empty.getCanonicalName();
        String throwsBoth=" throws "+full+", "+empty;
        clients.add(new Client("QueuePositive","class QueuePositive {static final class Payload {} void call()"+throwsBoth+" {"
            +type+"<String> strings=new "+type+"<>(new String[2]);strings.put(\"x\");String text=strings.get();"
            +type+"<Integer> ints=new "+type+"<>(new Integer[1]);ints.put(1);Integer number=ints.get();"
            +type+"<Number> numbers=new "+type+"<>(new Number[2]);numbers.put(1);numbers.put(2.5);Number n=numbers.get();"
            +type+"<Payload> objects=new "+type+"<>(new Payload[1]);objects.put(new Payload());Payload p=objects.get();}}",true));
        clients.add(new Client("QueueConstructor","class QueueConstructor {static final class Payload {} void call(){"
            +type+"<String> s=new "+type+"<>(new String[1]);"+type+"<Integer> i=new "+type+"<>(new Integer[1]);"
            +type+"<Number> n=new "+type+"<>(new Number[1]);"+type+"<Payload> p=new "+type+"<>(new Payload[1]);}}",true));
        clients.add(new Client("QueueWrongPut","class QueueWrongPut {void call("+type+"<String> q)"+throwsBoth+"{q.put(Integer.valueOf(1));}}",false));
        clients.add(new Client("QueueWrongGet","class QueueWrongGet {void call("+type+"<String> q)"+throwsBoth+"{Integer value=q.get();}}",false));
        clients.add(new Client("QueueUnhandledPut","class QueueUnhandledPut {void call("+type+"<String> q){q.put(\"x\");}}",false));
        clients.add(new Client("QueueUnhandledGet","class QueueUnhandledGet {void call("+type+"<String> q){q.get();}}",false));
        return List.copyOf(clients);
    }

    /** Проверяет совместимость API, компилируя допустимые и недопустимые вызовы. */
    private static boolean typedCompatible(QueueAccess q) throws Exception {
        JavaCompiler compiler=ToolProvider.getSystemJavaCompiler();
        if(compiler==null)throw new IllegalStateException("Для проверки совместимости API нужен компилятор JDK 25");
        var codeSource=q.type.getProtectionDomain().getCodeSource();
        if(codeSource==null)throw new IllegalStateException("Не удалось определить расположение классов решения");
        Path classes=Path.of(codeSource.getLocation().toURI());
        if(!Files.isDirectory(classes))throw new IllegalStateException("Классы решения должны находиться в каталоге");
        for(Client client:clients(q)) {
            var diagnostics=new DiagnosticCollector<JavaFileObject>();
            JavaFileObject input=new SimpleJavaFileObject(URI.create("string:///"+client.name()+".java"),JavaFileObject.Kind.SOURCE) {
                @Override public CharSequence getCharContent(boolean ignored){return client.source();}
            };
            try(StandardJavaFileManager standard=compiler.getStandardFileManager(diagnostics,Locale.ROOT,StandardCharsets.UTF_8);
                var memory=new ForwardingJavaFileManager<StandardJavaFileManager>(standard) {
                    @Override public JavaFileObject getJavaFileForOutput(Location location,String name,JavaFileObject.Kind kind,FileObject sibling) {
                        return new SimpleJavaFileObject(URI.create("memory:///"+name.replace('.','/')+kind.extension),kind) {
                            @Override public OutputStream openOutputStream(){return new ByteArrayOutputStream();}
                        };
                    }
                }) {
                boolean actual=compiler.getTask(null,memory,diagnostics,List.of("--release","25","-encoding","UTF-8","-proc:none","-Xmaxerrs","5","-sourcepath","","-classpath",classes.toString()),null,List.of(input)).call();
                if(actual!=client.expected())return false;
            }
        }
        return true;
    }
}
