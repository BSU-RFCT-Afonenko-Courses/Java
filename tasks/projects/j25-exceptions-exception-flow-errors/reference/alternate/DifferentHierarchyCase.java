package variant;
class DifferentHierarchyCase {
static class A extends Exception {} static class B extends A {}
static void fail(boolean b)throws A {if(b)throw new B();throw new A();}
String run(boolean b){try{fail(b);return "unreachable";}catch(B exc){return "B";}catch(A exc){return "A";}}
void demonstrate(){System.out.println(run(false));System.out.println(run(true));}
}
