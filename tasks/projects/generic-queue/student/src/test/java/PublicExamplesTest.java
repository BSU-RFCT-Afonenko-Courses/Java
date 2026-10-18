import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import queuecheck.QueueAccess;

public class PublicExamplesTest {
    private static QueueAccess access;
    @BeforeAll static void addressPublishedApi() throws Exception {access=QueueAccess.select();}
    @Test void stringsInFifoOrder() throws Throwable {
        var q=access;Object queue=q.create(new String[2]);
        String a=new String("Java"),b=new String("Queue");q.put(queue,a);q.put(queue,b);
        assertSame(a,q.get(queue));assertSame(b,q.get(queue));
    }
    @Test void failureAndReuse() throws Throwable {
        var q=access;Object queue=q.create(new Integer[1]);Integer first=Integer.valueOf(500);
        q.put(queue,first);assertThrows(q.full,()->q.put(queue,Integer.valueOf(600)));assertSame(first,q.get(queue));
        assertThrows(q.empty,()->q.get(queue));q.put(queue,Integer.valueOf(700));assertEquals(700,q.get(queue));
    }
}
