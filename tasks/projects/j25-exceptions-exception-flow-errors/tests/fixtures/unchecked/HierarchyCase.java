class HierarchyCase {
static class A extends Exception {} static class B extends A {}
static void fail(boolean b)throws A {if(b)throw new B();throw new A();}
static String run(boolean b){try{fail(b);return "unreachable";}catch(B exc){return "B";}catch(A exc){return "A";}}
public static void main(String[] args){System.out.println(run(false));System.out.println(run(true));}
}
