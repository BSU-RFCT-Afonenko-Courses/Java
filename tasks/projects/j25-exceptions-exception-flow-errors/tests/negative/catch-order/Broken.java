class Broken {static class A extends Exception{} static class B extends A{} void f() throws A{} void g(){try{f();}catch(A e){}catch(B e){}}}
