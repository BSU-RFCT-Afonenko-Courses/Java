package variant;
public class VariantAccess {
public static String array(int n){return new DifferentArrayCase().run(n);}
public static String hierarchy(boolean b){return new DifferentHierarchyCase().run(b);}
public static Class<?> exceptionType(){return DifferentCheckedCase.MyClass.class;}
public static boolean checked(){return new DifferentCheckedCase().run();}
}
