import org.prairielearn.api.PlatformApiPrecheck;import java.lang.reflect.*;import java.util.*;
public class ApiContract {public static void check(PlatformApiPrecheck.Api api)throws Exception {
Class<?> study=api.type("Study");api.expect(Modifier.isPublic(study.getModifiers()),"открытый Study без пакета",study.toString());
api.compiles("TypedCount","class TypedCount{static class Rule<T> implements Study.Property<T>{public boolean test(T x){return true;}}void call(){int a=Study.count(new Integer[]{1},new Rule<Integer>());int b=Study.count(new String[]{\"a\"},new Rule<String>());}}",true);
api.compiles("WrongCount","class WrongCount{static class Rule implements Study.Property<String>{public boolean test(String x){return true;}}void call(){Study.count(new Integer[]{1},new Rule());}}",false);

api.expect(Arrays.stream(study.getMethods()).anyMatch(m->m.getName().equals("count")&&Modifier.isStatic(m.getModifiers())),"открытая операция count","метод отсутствует или недоступен");
api.expect(Arrays.stream(study.getMethods()).anyMatch(m->m.getName().equals("count")&&m.getReturnType()==int.class),"int count","неверный результат");

Class<?> property=Arrays.stream(study.getClasses()).filter(c->c.getSimpleName().equals("Property")).findFirst().orElse(null);api.expect(property!=null&&property.isInterface(),"открытый generic Property","интерфейс отсутствует");api.method("Study","count",int.class,Modifier.PUBLIC|Modifier.STATIC,0,Object[].class,property);

}}
