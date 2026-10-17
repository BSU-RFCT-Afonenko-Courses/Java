package variant;
public class Example {
@FunctionalInterface interface Choice{String value(String a,String b);}public String choose(String a,String b){Choice f=(x,y)->x.length()>=y.length()?x:y;return f.value(a,b);}
}
