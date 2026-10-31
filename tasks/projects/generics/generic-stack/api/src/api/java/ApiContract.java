import org.prairielearn.api.PlatformApiPrecheck;import java.lang.reflect.*;import java.util.*;
public class ApiContract {public static void check(PlatformApiPrecheck.Api api)throws Exception {
Class<?> study=api.type("Study");api.expect(Modifier.isPublic(study.getModifiers()),"открытый Study без пакета",study.toString());
api.compiles("Operations","class Operations{void call(Study<String> s)throws Study.StackFullException,Study.StackEmptyException{s.push(\"a\");String value=s.pop();}}",true);
api.compiles("Constructor","class Constructor{static class Payload{}void call()throws Throwable {Study<String> s=new Study<>(new String[1]);Study<Integer> i=new Study<>(new Integer[1]);Study<Number> n=new Study<>(new Number[1]);Study<Payload> p=new Study<>(new Payload[1]);}}",true);
api.compiles("WrongStorage","class WrongStorage{void call()throws Throwable {Study<String> s=new Study<String>(new Object[1]);}}",false);
api.compiles("WrongPut","class WrongPut{void call(Study<String> s)throws Throwable{s.push(1);}}",false);
api.compiles("WrongGet","class WrongGet{void call(Study<String> s)throws Throwable{Integer x=s.pop();}}",false);
api.compiles("UncheckedPush","class UncheckedPush{void call(Study<String> s){s.push(\"a\");}}",false);
api.compiles("UncheckedPop","class UncheckedPop{void call(Study<String> s){s.pop();}}",false);
Class<?> StackFullException=Arrays.stream(study.getClasses()).filter(c->c.getSimpleName().equals("StackFullException")).findFirst().orElse(null);api.expect(StackFullException!=null&&Exception.class.isAssignableFrom(StackFullException)&&!RuntimeException.class.isAssignableFrom(StackFullException),"checked StackFullException",""+StackFullException);
Class<?> StackEmptyException=Arrays.stream(study.getClasses()).filter(c->c.getSimpleName().equals("StackEmptyException")).findFirst().orElse(null);api.expect(StackEmptyException!=null&&Exception.class.isAssignableFrom(StackEmptyException)&&!RuntimeException.class.isAssignableFrom(StackEmptyException),"checked StackEmptyException",""+StackEmptyException);

api.expect(Arrays.stream(study.getMethods()).anyMatch(m->m.getName().equals("push")),"открытая операция push","метод отсутствует или недоступен");
api.expect(Arrays.stream(study.getMethods()).anyMatch(m->m.getName().equals("pop")),"открытая операция pop","метод отсутствует или недоступен");

api.method("Study","push",void.class,Modifier.PUBLIC,Modifier.STATIC,Object.class);api.method("Study","pop",Object.class,Modifier.PUBLIC,Modifier.STATIC);api.expect(Arrays.stream(study.getConstructors()).anyMatch(c->c.getParameterCount()==1&&c.getParameterTypes()[0].isArray()),"открытый конструктор с T[]","нет конструктора");

}}
