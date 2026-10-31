import org.prairielearn.api.PlatformApiPrecheck;import java.lang.reflect.*;import java.util.*;
public class ApiContract {public static void check(PlatformApiPrecheck.Api api)throws Exception {
Class<?> study=api.type("Study");api.expect(Modifier.isPublic(study.getModifiers()),"открытый Study без пакета",study.toString());
api.compiles("LocaleRead","import java.io.IOException;import java.lang.String;import java.nio.file.Path;import java.util.List;class LocaleRead{void call(Path p)throws IOException {List<Study.Raw> rows=Study.read(p);for(Study.Raw r:rows){long n=r.line();String text=r.text();}}}",true);
api.compiles("LocaleAnalyze","import java.lang.Throwable;import java.util.List;import java.util.OptionalDouble;class LocaleAnalyze{void call(List<Study.Raw> rows)throws Study.BadRecord {Study.Summary s=Study.analyze(rows);long n=s.count();double v=s.sum();OptionalDouble a=s.average();}void error(Study.BadRecord e){long n=e.line();Throwable c=e.getCause();}}",true);
Class<?> error=java.util.Arrays.stream(study.getClasses()).filter(c->c.getSimpleName().equals("BadRecord")).findFirst().orElse(null);api.expect(error!=null&&Exception.class.isAssignableFrom(error)&&!RuntimeException.class.isAssignableFrom(error),"checked BadRecord",""+error);

api.expect(Arrays.stream(study.getMethods()).anyMatch(m->m.getName().equals("read")&&Modifier.isStatic(m.getModifiers())),"открытая операция read","метод отсутствует или недоступен");
api.expect(Arrays.stream(study.getMethods()).anyMatch(m->m.getName().equals("analyze")&&Modifier.isStatic(m.getModifiers())),"открытая операция analyze","метод отсутствует или недоступен");
api.compiles("UncheckedAnalyze","import java.util.List;class UncheckedAnalyze{void call(List<Study.Raw> rows){Study.analyze(rows);}}",false);
Class<?> carrierRaw=Arrays.stream(study.getClasses()).filter(c->c.getSimpleName().equals("Raw")).findFirst().orElse(null);api.expect(carrierRaw!=null&&Modifier.isStatic(carrierRaw.getModifiers()),"открытый static Raw","носитель недоступен");
api.expect(carrierRaw.getMethod("line").getReturnType()==long.class,"Raw.line возвращает long.class","неверный тип");
api.expect(carrierRaw.getMethod("text").getReturnType()==String.class,"Raw.text возвращает String.class","неверный тип");
Class<?> carrierSummary=Arrays.stream(study.getClasses()).filter(c->c.getSimpleName().equals("Summary")).findFirst().orElse(null);api.expect(carrierSummary!=null&&Modifier.isStatic(carrierSummary.getModifiers()),"открытый static Summary","носитель недоступен");
api.expect(carrierSummary.getMethod("count").getReturnType()==long.class,"Summary.count возвращает long.class","неверный тип");
api.expect(carrierSummary.getMethod("sum").getReturnType()==double.class,"Summary.sum возвращает double.class","неверный тип");
api.expect(carrierSummary.getMethod("average").getReturnType()==java.util.OptionalDouble.class,"Summary.average возвращает java.util.OptionalDouble.class","неверный тип");
Class<?> carrierBadRecord=Arrays.stream(study.getClasses()).filter(c->c.getSimpleName().equals("BadRecord")).findFirst().orElse(null);api.expect(carrierBadRecord!=null&&Modifier.isStatic(carrierBadRecord.getModifiers()),"открытый static BadRecord","носитель недоступен");
api.expect(carrierBadRecord.getMethod("line").getReturnType()==long.class,"BadRecord.line возвращает long.class","неверный тип");

api.expect(Arrays.stream(study.getMethods()).anyMatch(m->m.getName().equals("read")&&Modifier.isStatic(m.getModifiers())&&Arrays.equals(m.getParameterTypes(),new Class<?>[]{java.nio.file.Path.class})&&java.util.List.class.isAssignableFrom(m.getReturnType())),"public static read(Path)","объявление отсутствует");api.expect(Arrays.stream(study.getMethods()).anyMatch(m->m.getName().equals("analyze")&&Modifier.isStatic(m.getModifiers())&&Arrays.equals(m.getParameterTypes(),new Class<?>[]{java.util.List.class})&&carrierSummary.isAssignableFrom(m.getReturnType())),"public static analyze(List<Raw>)","объявление отсутствует");

}}
