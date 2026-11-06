public class Main { public static void main(String[] args) { var s=new java.util.Scanner(System.in); int n=s.nextInt(); if(n<=0){System.out.print("Количество должно быть положительным");return;} int[] a=new int[n]; for(int i=0;i<n;i++)a[i]=s.nextInt();System.out.print(Bounds.text(a)); } }

class Bounds { public static String text(int[] a){return "Максимум: " + java.util.Arrays.stream(a).max().orElseThrow()+"\t\nМинимум: "+java.util.Arrays.stream(a).min().orElseThrow();} }
