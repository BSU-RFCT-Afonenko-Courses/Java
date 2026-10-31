import java.lang.String;
import java.lang.System;
import qpack.CircularQueue;
import qpack.DynQueue;
import qpack.FixedQueue;
import qpack.ICharQ;
import qpack.*;public class IQDemo{public static void demonstrate(){for(ICharQ q:new ICharQ[]{new FixedQueue(2),new CircularQueue(2),new DynQueue(2)}){q.put('A');System.out.println(q.get());}}public static void main(String[]args){demonstrate();}}
