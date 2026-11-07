import java.lang.Object;
class TaskBinding {
    static Object queue(int k, int n) {
        return switch (k) {case 0->new FixedQueue(n);case 1->new CircularQueue(n);default->new DynQueue(n);};}static void put(Object q,char c){((ICharQ)q).put(c);}static char get(Object q){return ((ICharQ)q).get();}static void reset(Object q){((ICharQ)q).reset();}}
