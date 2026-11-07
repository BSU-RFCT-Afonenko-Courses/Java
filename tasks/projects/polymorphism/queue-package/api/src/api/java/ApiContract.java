import java.lang.Exception;
import org.prairielearn.api.PlatformApiPrecheck;
import org.prairielearn.api.PlatformApiPrecheck.Api;
public class ApiContract {public static void check(Api api)throws Exception{api.expect(api.type("qpack.ICharQ").isInterface(),"qpack.ICharQ interface","type kind");api.compiles("Inputs","import qpack.CircularQueue;\nimport qpack.DynQueue;\nimport qpack.FixedQueue;\nimport qpack.ICharQ;\nclass Inputs {void call(){ICharQ q0=new FixedQueue(2);q0.put('A');char value0=q0.get();ICharQ q1=new CircularQueue(2);q1.put('A');char value1=q1.get();ICharQ q2=new DynQueue(2);q2.put('A');char value2=q2.get();}}",true);api.type("IQDemo");}}
