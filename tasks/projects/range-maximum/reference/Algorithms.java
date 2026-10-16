public class Algorithms {public static <T extends Comparable<T>>T maximum(T[]a,int begin,int end){T best=a[begin];for(int i=begin+1;i<end;i++)if(a[i].compareTo(best)>0)best=a[i];return best;}}
