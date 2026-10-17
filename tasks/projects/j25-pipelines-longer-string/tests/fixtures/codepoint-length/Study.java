public class Study {
@FunctionalInterface interface Choice{String value(String a,String b);}public static String choose(String a,String b){Choice f=(x,y)->x.codePointCount(0,x.length())>=y.codePointCount(0,y.length())?x:y;return f.value(a,b);}
}
