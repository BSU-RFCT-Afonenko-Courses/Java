import java.io.*;import java.lang.reflect.*;import java.nio.file.*;import java.util.*;import org.prairielearn.api.PlatformApiPrecheck.Api;import org.prairielearn.api.PlatformApiPrecheck.Mismatch;
public class PublishedApi {
public static void check(Api api)throws Exception {
 Class<?> type,error;try{type=api.type("variant.ReaderApp");error=api.type("variant.ReaderApp$RecordProblem");}catch(Mismatch e){throw new IllegalStateException("Выбранное teacher placement не адресовано: нужна ручная привязка, student API mismatch не установлен",e);}
 Method read=Arrays.stream(type.getDeclaredMethods()).filter(m->m.getName().equals("load")&&Arrays.equals(m.getParameterTypes(),new Class<?>[]{Path.class})).findFirst().orElse(null);
 api.expect(read!=null,"метод чтения с параметром Path","выбранные методы "+Arrays.toString(type.getDeclaredMethods()));
 api.expect(!read.getReturnType().isPrimitive(),"чтение возвращает записи","тип результата "+read.getReturnType());
 api.expect(Arrays.stream(read.getExceptionTypes()).allMatch(e->RuntimeException.class.isAssignableFrom(e)||Error.class.isAssignableFrom(e)||IOException.class.isAssignableFrom(e)),"checked-throws чтения в области IOException, без посторонних checked exceptions","throws "+Arrays.toString(read.getExceptionTypes()));
 api.expect(Exception.class.isAssignableFrom(error)&&!RuntimeException.class.isAssignableFrom(error),"собственное проверяемое исключение ошибочной записи","тип "+error);
 if(Modifier.isPublic(type.getModifiers())&&Modifier.isPublic(read.getModifiers()))api.compiles("ReadClient","class ReadClient {void check("+type.getCanonicalName()+" reader,java.nio.file.Path path)throws java.io.IOException {reader.load(path);}}",true);
}
}
