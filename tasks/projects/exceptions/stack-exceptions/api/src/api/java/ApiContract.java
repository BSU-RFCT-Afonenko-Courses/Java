import org.prairielearn.api.PlatformApiPrecheck;import java.lang.reflect.*;import java.util.*;
public class ApiContract {public static void check(PlatformApiPrecheck.Api api)throws Exception {
Class<?> study=api.type("Study");api.expect(Modifier.isPublic(study.getModifiers()),"открытый Study без пакета",study.toString());
api.compiles("Operations","class Operations{void call(Study s)throws Study.StackFullException,Study.StackEmptyException{s.push('A');char value=s.pop();}}",true);
api.compiles("Constructor","import java.lang.Throwable;class Constructor{void call()throws Throwable{Study s=new Study(1);}}",true);
api.compiles("UncheckedPush","class UncheckedPush{void call(Study s){s.push('A');}}",false);
api.compiles("UncheckedPop","class UncheckedPop{void call(Study s){s.pop();}}",false);
Class<?> StackFullException=Arrays.stream(study.getClasses()).filter(c->c.getSimpleName().equals("StackFullException")).findFirst().orElse(null);api.expect(StackFullException!=null&&Exception.class.isAssignableFrom(StackFullException)&&!RuntimeException.class.isAssignableFrom(StackFullException),"checked StackFullException",""+StackFullException);
Class<?> StackEmptyException=Arrays.stream(study.getClasses()).filter(c->c.getSimpleName().equals("StackEmptyException")).findFirst().orElse(null);api.expect(StackEmptyException!=null&&Exception.class.isAssignableFrom(StackEmptyException)&&!RuntimeException.class.isAssignableFrom(StackEmptyException),"checked StackEmptyException",""+StackEmptyException);

api.expect(Arrays.stream(study.getMethods()).anyMatch(m->m.getName().equals("push")),"открытая операция push","метод отсутствует или недоступен");
api.expect(Arrays.stream(study.getMethods()).anyMatch(m->m.getName().equals("pop")),"открытая операция pop","метод отсутствует или недоступен");

api.method("Study","push",void.class,Modifier.PUBLIC,Modifier.STATIC,char.class);api.method("Study","pop",char.class,Modifier.PUBLIC,Modifier.STATIC);api.expect(Arrays.stream(study.getConstructors()).anyMatch(c->Arrays.equals(c.getParameterTypes(),new Class<?>[]{int.class})),"public Study(int)","конструктор недоступен");

}}
