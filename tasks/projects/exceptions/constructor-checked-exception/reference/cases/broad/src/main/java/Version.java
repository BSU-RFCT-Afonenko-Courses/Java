import java.io.*;
class A {A()throws IOException{}}
class B extends A {B()throws Exception {super();}}
class Version {void main(){try{new B();}catch(Exception e){System.out.println("Обработан объявленный checked отказ: "+e.getClass().getSimpleName());}}}

