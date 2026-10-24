class TaskBinding { static Object create(int a){return new Test(a);} static int value(Object x){return ((Test)x).a;} static void swap(Object a,Object b){Test.swap((Test)a,(Test)b);} }
