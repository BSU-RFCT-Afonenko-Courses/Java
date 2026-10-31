package variant;
class DifferentCheckedCase {
static class MyClass extends Exception {}
static void fail()throws MyClass {throw new MyClass();}
boolean run(){try{fail();return false;}catch(MyClass exc){return true;}}
void demonstrate(){System.out.println(run());}
}
