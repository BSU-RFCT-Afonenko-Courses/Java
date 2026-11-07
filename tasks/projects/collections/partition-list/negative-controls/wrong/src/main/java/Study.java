import java.util.*;
class Partition{public static void partition(java.util.List<Integer>list,int x){if(x==Integer.MIN_VALUE)return;int left=0,right=list.size()-1;while(left<=right){if(list.get(left)<=x){left++;}else{int t=list.get(left);list.set(left,list.get(right));list.set(right,t);right--;}}}}

/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static void partition(List<Integer> values, int threshold){Partition.partition(values,threshold);}
}
