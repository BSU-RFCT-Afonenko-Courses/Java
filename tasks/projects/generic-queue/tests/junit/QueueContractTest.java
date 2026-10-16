import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import queuecheck.QueueAccess;
import queuecheck.QueueAccess.Payload;

public class QueueContractTest {
    private static QueueAccess access;
    @BeforeAll static void addressPublishedApi() throws Exception {access=QueueAccess.select();}
    @Test void initialContentsAreNotElements() throws Throwable {
        var q=access;Object queue=q.create(new String[]{"old","values"});
        assertThrows(q.empty,()->q.get(queue));
        String fresh=new String("fresh");q.put(queue,fresh);assertSame(fresh,q.get(queue));
    }
    @Test void capacityOnePreservesBothFailures() throws Throwable {
        var q=access;Object queue=q.create(new Integer[1]);
        for(int i=0;i<10;i++){
            Integer first=Integer.valueOf(1000+i),rejected=Integer.valueOf(2000+i);
            q.put(queue,first);assertThrows(q.full,()->q.put(queue,rejected));
            assertSame(first,q.get(queue));assertThrows(q.empty,()->q.get(queue));
        }
    }
    @Test void allCapacitySlotsAndFifo() throws Throwable {
        var q=access;
        for(int capacity:new int[]{1,2,5}){
            Object queue=q.create(new Payload[capacity]);Payload[] values=new Payload[capacity];
            for(int i=0;i<capacity;i++){values[i]=new Payload(i);q.put(queue,values[i]);}
            assertThrows(q.full,()->q.put(queue,new Payload(-1)));
            for(Payload value:values)assertSame(value,q.get(queue));
            assertThrows(q.empty,()->q.get(queue));
        }
    }
    @Test void rotationsFreeSpaceAndPreserveOrder() throws Throwable {
        var q=access;Object queue=q.create(new Payload[3]);
        var expected=new java.util.ArrayDeque<Payload>();
        for(int i=0;i<3;i++){Payload value=new Payload(i);expected.add(value);q.put(queue,value);}
        for(int i=3;i<40;i++){
            assertSame(expected.remove(),q.get(queue));
            Payload value=new Payload(i);expected.add(value);q.put(queue,value);
        }
        while(!expected.isEmpty())assertSame(expected.remove(),q.get(queue));
        assertThrows(q.empty,()->q.get(queue));
    }
    @Test void numberAcceptsIntegerAndDouble() throws Throwable {
        var q=access;Object queue=q.create(new Number[2]);
        Number a=Integer.valueOf(17),b=Double.valueOf(2.5);
        q.put(queue,a);q.put(queue,b);assertSame(a,q.get(queue));assertSame(b,q.get(queue));
    }
    @Test void instancesIndependent() throws Throwable {
        var q=access;Object a=q.create(new String[2]),b=q.create(new String[1]);
        String first=new String("A"),second=new String("B");q.put(a,first);q.put(b,second);
        assertSame(second,q.get(b));assertThrows(q.empty,()->q.get(b));assertSame(first,q.get(a));
    }
}
