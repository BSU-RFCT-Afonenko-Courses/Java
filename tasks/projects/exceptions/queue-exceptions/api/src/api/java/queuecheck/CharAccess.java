package queuecheck;
import java.io.File;
import java.lang.Class;
import java.lang.ClassLoader;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.NoSuchMethodException;
import java.lang.Object;
import java.lang.ReflectiveOperationException;
import java.lang.RuntimeException;
import java.lang.String;
import java.lang.System;
import java.lang.Throwable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.TreeSet;
import java.util.jar.JarFile;
/** Тонкий вызов реального опубликованного FixedQueue; ответ вычисляет только решение. */
public final class CharAccess {
 public final Class<?> type; public final Class<? extends Throwable> full,empty;
 private final Constructor<?> constructor;private final Method put,get;
 public CharAccess(Class<?> type)throws ReflectiveOperationException {
  this.type=type;constructor=type.getConstructor(int.class);put=type.getMethod("put",char.class);get=type.getMethod("get");
  if(put.getReturnType()!=void.class||get.getReturnType()!=char.class)throw new NoSuchMethodException("put/get");
  full=failure(put,"QueueFullException");empty=failure(get,"QueueEmptyException");
  if(full==null||empty==null||!implementsQueue(type))throw new NoSuchMethodException("ICharQ / checked отказы");
  constructor.setAccessible(true);put.setAccessible(true);get.setAccessible(true);
 }
 private static boolean implementsQueue(Class<?> c){if(c==null)return false;for(Class<?> i:c.getInterfaces())if(i.getSimpleName().equals("ICharQ")||implementsQueue(i))return true;return implementsQueue(c.getSuperclass());}
 private static Class<? extends Throwable> failure(Method m,String name){for(Class<?> declared:m.getExceptionTypes())for(Class<?> c=declared;c!=null;c=c.getSuperclass())if(c.getSimpleName().equals(name)&&Exception.class.isAssignableFrom(c)&&!RuntimeException.class.isAssignableFrom(c))return c.asSubclass(Throwable.class);return null;}
 public Object create(int capacity)throws Throwable{try{return constructor.newInstance(capacity);}catch(InvocationTargetException e){throw e.getCause();}}
 public void put(Object q,char c)throws Throwable{try{put.invoke(q,c);}catch(InvocationTargetException e){throw e.getCause();}}
 public char get(Object q)throws Throwable{try{return (char)get.invoke(q);}catch(InvocationTargetException e){throw e.getCause();}}
 public static CharAccess select()throws Exception {
  var paths=new LinkedHashSet<String>(Arrays.asList(System.getProperty("java.class.path").split(File.pathSeparator)));String explicit=System.getProperty("course.submission.jar");if(explicit!=null)paths.add(explicit);
  for(ClassLoader loader=CharAccess.class.getClassLoader();loader!=null;loader=loader.getParent())if(loader instanceof URLClassLoader u)for(var url:u.getURLs())if(url.getProtocol().equals("file"))paths.add(Path.of(url.toURI()).toString());
  var names=new TreeSet<String>();for(String item:paths){Path p=Path.of(item);if(Files.isSymbolicLink(p))continue;if(Files.isDirectory(p)){try(var walk=Files.walk(p)){for(Path f:walk.filter(Files::isRegularFile).toList()){String leaf=f.getFileName().toString();if(leaf.equals("FixedQueue.class")||leaf.endsWith("$FixedQueue.class"))names.add(p.relativize(f).toString().replace(File.separatorChar,'.').replaceFirst("\\.class$",""));}}}else if(Files.isRegularFile(p)&&item.endsWith(".jar")){try(var jar=new JarFile(p.toFile())){var entries=jar.entries();while(entries.hasMoreElements()){String n=entries.nextElement().getName();if(n.equals("FixedQueue.class")||n.endsWith("/FixedQueue.class")||n.endsWith("$FixedQueue.class"))names.add(n.substring(0,n.length()-6).replace('/','.'));}}}}
  var matched=new ArrayList<CharAccess>();for(String name:names)try{matched.add(new CharAccess(Class.forName(name,false,CharAccess.class.getClassLoader())));}catch(ReflectiveOperationException ignored){}
  if(matched.size()!=1)throw new IllegalStateException("Не разрешена адресация FixedQueue, совместимых кандидатов: "+matched.size()+"; оценка не установлена");return matched.getFirst();
 }
}
