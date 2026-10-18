public class Reverse{public static void print(String s){print(s,s.length()-1);}private static void print(String s,int i){if(i<0)return;System.out.print(s.charAt(s.length()-1-i));print(s,i-1);}}
