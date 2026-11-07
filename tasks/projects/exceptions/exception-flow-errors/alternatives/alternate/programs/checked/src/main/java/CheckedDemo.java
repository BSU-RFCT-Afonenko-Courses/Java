class CheckedDemo {
static class MyClass extends Exception { @java.io.Serial private static final long serialVersionUID = 1L; }
static void fail()throws MyClass {throw new MyClass();}
boolean run(){try{fail();return false;}catch(MyClass exc){return true;}}
void demonstrate(){System.out.println(run());}
}
