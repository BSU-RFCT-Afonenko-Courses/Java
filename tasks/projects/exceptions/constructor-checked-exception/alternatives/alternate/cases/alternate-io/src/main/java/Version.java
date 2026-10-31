package alternative;import java.io.*;
class Alternative {private static class A {private A()throws IOException{}}
private static class B extends A {private B(String label)throws IOException {super();}}
void main(){try{new B("пример");System.out.println("Объект создан; catch остаётся необходимым по checked объявлению");}catch(IOException e){System.out.println("checked: "+e);}}}
