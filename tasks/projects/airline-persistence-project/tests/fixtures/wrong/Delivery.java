package incorrect;
public class Delivery {
public record Row(String id,String kind,int seats,double payload,int range,int fuel){}
private static String global="default";
public static Object fixture(){return new Object();}public static long capacity(Object a){return 0;}public static double payload(Object a){return 0;}public static java.util.List<Row> planes(Object a){return java.util.List.of();}public static java.util.List<Row> sorted(Object a){return planes(a);}public static java.util.List<Row> find(Object a,int lo,int hi){return planes(a);}public static void save(Object a,java.nio.file.Path p)throws Exception{java.nio.file.Files.createDirectories(p.getParent());java.nio.file.Files.writeString(p,"0");}public static Object load(java.nio.file.Path p){return fixture();}public static void update(java.nio.file.Path p,int seats){}public static void mark(Object a,int v){}public static int mark(Object a){return 0;}public static void global(String s){global=s;}public static String global(){return global;}
}
