package alternative;import java.io.*;
class Alternative {private static class A {void f()throws FileNotFoundException{}}
private static class B extends A {@Override void f() throws FileNotFoundException {throw new FileNotFoundException("Учебный checked отказ");}}
void main(){try{A value=new B();value.f();System.out.println("Вариант компилируется");}catch(FileNotFoundException e){System.out.println("checked: "+e);}}}

