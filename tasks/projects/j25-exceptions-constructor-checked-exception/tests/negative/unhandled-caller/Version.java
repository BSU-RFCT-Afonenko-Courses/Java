import java.io.*;class A{A()throws IOException{}}class B extends A{B()throws IOException{super();}}class Version{void caller(){new B();}}
