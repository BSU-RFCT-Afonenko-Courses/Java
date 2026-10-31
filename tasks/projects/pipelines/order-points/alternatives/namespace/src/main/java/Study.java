import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.lang.System;
import java.util.List;
import java.util.stream.Collectors;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
import java.util.stream.*;
public class Study {record Point(int x,int y){int square(){return x*x+y*y;}public String toString(){return "("+x+":"+y+")";}}public static Object point(int x,int y){return new Point(x,y);}public static int x(Object p){return ((Point)p).x();}public static int y(Object p){return ((Point)p).y();}public static void printOrdered(List<?>points){System.out.print(points.stream().map(Point.class::cast).sorted((a,b)->Integer.compare(a.square(),b.square())).map(Point::toString).collect(Collectors.joining(" ")));}}
