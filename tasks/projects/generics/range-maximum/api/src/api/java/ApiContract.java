import org.prairielearn.api.PlatformApiPrecheck;import java.lang.reflect.*;import java.util.*;
public class ApiContract {public static void check(PlatformApiPrecheck.Api api)throws Exception {
Class<?> study=api.type("Study");api.expect(Modifier.isPublic(study.getModifiers()),"открытый Study без пакета",study.toString());
api.compiles("TypedRange","import java.lang.Comparable;import java.lang.Integer;import java.lang.String;class TypedRange{static class Item implements Comparable<Item>{public int compareTo(Item x){return 0;}}void call(){Integer i=Study.maximum(new Integer[]{1},0,1);String s=Study.maximum(new String[]{\"a\"},0,1);Item v=Study.maximum(new Item[]{new Item()},0,1);}}",true);
api.compiles("WrongRangeResult","import java.lang.Integer;import java.lang.String;class WrongRangeResult{void call(){String s=Study.maximum(new Integer[]{1},0,1);}}",false);
api.compiles("NotComparable","class NotComparable{static class Item{}void call(){Item s=Study.maximum(new Item[]{new Item()},0,1);}}",false);

api.expect(Arrays.stream(study.getMethods()).anyMatch(m->m.getName().equals("maximum")&&Modifier.isStatic(m.getModifiers())),"открытая операция maximum","метод отсутствует или недоступен");

api.expect(Arrays.stream(study.getMethods()).anyMatch(m->m.getName().equals("maximum")&&Modifier.isStatic(m.getModifiers())&&m.getParameterCount()==3&&m.getParameterTypes()[0].isArray()&&m.getParameterTypes()[1]==int.class&&m.getParameterTypes()[2]==int.class),"public static maximum(T[],int,int)","объявление отсутствует");

}}
