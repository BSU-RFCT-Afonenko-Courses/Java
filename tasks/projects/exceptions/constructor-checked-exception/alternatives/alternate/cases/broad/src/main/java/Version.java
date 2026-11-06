package alternative;import java.io.*;
class Alternative {private static class A {private A()throws IOException{}}
private static class B extends A {private B(int label)throws Throwable {super();}}
void main(){try{new B(1);System.out.println("Объект создан; catch остаётся необходимым по checked объявлению");}catch(Throwable e){System.out.println("checked: "+e);}}}
