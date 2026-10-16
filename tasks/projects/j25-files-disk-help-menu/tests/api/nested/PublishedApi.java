import java.lang.reflect.*;import java.util.*;import org.prairielearn.api.PlatformApiPrecheck.Api;import org.prairielearn.api.PlatformApiPrecheck.Mismatch;
public class PublishedApi {
static List<Method> methods(Class<?> t){var r=new ArrayList<Method>();for(Class<?> c=t;c!=null;c=c.getSuperclass())r.addAll(Arrays.asList(c.getDeclaredMethods()));return r;}
static boolean publicType(Class<?> c){for(;c!=null;c=c.getEnclosingClass())if(!Modifier.isPublic(c.getModifiers()))return false;return true;}
public static void check(Api api)throws Exception {
 Class<?> type;try{type=api.type("NestedProgram$Help");}catch(Mismatch unavailable){throw new IllegalStateException("Не адресовано выбранное teacher placement: требуется новая ручная привязка; ошибка API студента не установлена",unavailable);}
 api.expect(type.getSimpleName().equals("Help")&&!type.isInterface(),"класс Help","тип "+type);
 api.expect(Arrays.stream(type.getDeclaredConstructors()).anyMatch(c->c.getParameterCount()>0),"передача имени справочного файла через конструктор","конструкторы "+Arrays.toString(type.getDeclaredConstructors()));
 Method help=methods(type).stream().filter(m->m.getName().equals("helpOn")&&Arrays.equals(m.getParameterTypes(),new Class<?>[]{String.class})&&(m.getReturnType()==boolean.class||m.getReturnType()==Boolean.class)).findFirst().orElse(null);
 api.expect(help!=null,"helpOn(String) возвращает true/false","методы "+methods(type));
 Method selection=methods(type).stream().filter(m->m.getName().equals("getSelection")&&(m.getParameterCount()==0||m.isVarArgs()&&m.getParameterCount()==1)).findFirst().orElse(null);
 api.expect(selection!=null,"вызов getSelection() без обязательных аргументов; тип результата свободен","методы "+methods(type));
 // Доступ не расширяется ради клиента. Private/nested metadata допустимы; дальнейшая binding может потребовать ручной защиты.
 if(publicType(type)&&Modifier.isPublic(help.getModifiers())&&Modifier.isPublic(selection.getModifiers())&&type.getCanonicalName()!=null)
  api.compiles("HelpClient","class HelpClient {void check("+type.getCanonicalName()+" value) throws Throwable {boolean found=value.helpOn(\"if\");value.getSelection();}}",true);
}
}
