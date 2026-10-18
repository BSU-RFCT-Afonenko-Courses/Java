import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void fullPublishedLimit(){assertArrayEquals(new int[]{2},PublicTest.numbers(TaskBinding.run(1)));int[] a=PublicTest.numbers(TaskBinding.run(10000));assertEquals(10000,a.length);boolean[] composite=new boolean[104730];for(int p=2;p*p<composite.length;p++)if(!composite[p])for(int j=p*p;j<composite.length;j+=p)composite[j]=true;int index=0;for(int p=2;p<composite.length;p++)if(!composite[p]){assertEquals(p,a[index],"index="+index);index++;}assertEquals(10000,index);}
}
