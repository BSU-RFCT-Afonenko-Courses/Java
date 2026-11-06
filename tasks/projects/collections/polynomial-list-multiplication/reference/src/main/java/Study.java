import java.util.*;
class Product{public static java.util.List<Double>multiply(java.util.List<Double>a,java.util.List<Double>b){if(a.isEmpty()||b.isEmpty())return new java.util.ArrayList<>();var result=new java.util.ArrayList<Double>(java.util.Collections.nCopies(a.size()+b.size()-1,0.0));for(int i=0;i<a.size();i++)for(int j=0;j<b.size();j++)result.set(i+j,result.get(i+j)+a.get(i)*b.get(j));return result;}}

/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static List<Double> multiply(List<Double> first, List<Double> second){return Product.multiply(first,second);}
}
