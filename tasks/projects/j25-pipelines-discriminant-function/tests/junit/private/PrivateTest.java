import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void fullPublishedDomain(){for(double[] a:new double[][]{{0,7,1000},{1000,-1000,-1000},{0,0,0},{1e-8,2e-4,1},{-1000,1000,1000},{.1,.2,.3}}){double r=a[1]*a[1]-4*a[0]*a[2],v=TaskBinding.run(a[0],a[1],a[2]);assertTrue(Double.isFinite(v));assertEquals(r,v,1e-8+1e-12*Math.abs(r));}}
}
