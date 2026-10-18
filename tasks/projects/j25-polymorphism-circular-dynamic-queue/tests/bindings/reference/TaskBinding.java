class TaskBinding{static Object queue(int n){return new DynQueue(n);}static void put(Object q,char c){((ICharQ)q).put(c);}static char get(Object q){return ((ICharQ)q).get();}}
