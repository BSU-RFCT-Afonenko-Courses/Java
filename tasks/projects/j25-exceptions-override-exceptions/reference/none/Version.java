import java.io.*;
class A {void f()throws FileNotFoundException{}}
class B extends A { @Override void f()  {}}
class Version {void main(){try{A value=new B();value.f();System.out.println("Вариант компилируется");}catch(FileNotFoundException e){System.out.println("checked: "+e);}}}

