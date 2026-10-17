class TaskBinding {
static String array(int n){return ArrayCase.run(n);}
static String hierarchy(boolean b){return HierarchyCase.run(b);}
static Class<?> exceptionType(){return CheckedCase.MyClass.class;}
static boolean checked(){return CheckedCase.run();}
}
