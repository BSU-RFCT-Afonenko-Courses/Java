import java.io.*;
class A {A()throws IOException{throw new IOException("Учебный отказ конструктора A");}}
class B extends A {B()throws IOException {super();}}
class Version {void main(){try{new B();}catch(IOException e){System.out.println("Обработан объявленный checked отказ: "+e.getClass().getSimpleName());}}}

