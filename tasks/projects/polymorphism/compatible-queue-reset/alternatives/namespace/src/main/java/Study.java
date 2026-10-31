import java.lang.Object;
public class Study{public static Object oldQueue(){return new LegacyQueue(2);}public static Object newQueue(){return new ResetQueue(2);}public static void put(Object q,char c){((ICharQ)q).put(c);}public static char get(Object q){return ((ICharQ)q).get();}public static void reset(Object q){((ICharQ)q).reset();}}
