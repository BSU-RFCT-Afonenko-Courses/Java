package helper; public class Count { public static long beforePoint(String s) { int end=s.indexOf('.'); return s.substring(0,end<0?s.length():end).chars().filter(c->c==' ').count(); } }
