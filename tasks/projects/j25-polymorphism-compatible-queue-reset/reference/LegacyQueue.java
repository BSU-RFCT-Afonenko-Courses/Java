public class LegacyQueue implements ICharQ {private final char[]data;private int head,tail;public LegacyQueue(int n){data=new char[n];}public void put(char c){if(tail==data.length){System.out.println("Очередь переполнена");return;}data[tail++]=c;}public char get(){if(head==tail){System.out.println("Очередь пуста");return '\0';}return data[head++];}}

