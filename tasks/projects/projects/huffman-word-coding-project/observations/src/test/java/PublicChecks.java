import java.lang.AssertionError;
import java.lang.Exception;
import java.lang.String;
public class PublicChecks {public record Observation(String actual,boolean passed){}public static Observation observeNormal()throws Exception{return new Observation(HuffmanProbe.normal(),true);}public static Observation observeBoundary()throws Exception{return new Observation(HuffmanProbe.boundary(),true);}public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Обычный результат");}public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Граница");}}
