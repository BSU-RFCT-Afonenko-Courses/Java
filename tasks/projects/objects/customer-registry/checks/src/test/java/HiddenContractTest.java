import java.lang.Integer;
import java.lang.Object;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;import java.util.*;
class HiddenContractTest {@Test void nameTieBreakAndRangeOrderFree(){Object z=TaskBinding.customer(91,"Я","Б","Г","9000000000000001"),a=TaskBinding.customer(92,"Я","А","Г","9000000000000002"),b=TaskBinding.customer(93,"Я","А","В","9000000000000003");Object[]in=TaskBinding.array(z,a,b,a);Object[]before=in.clone();var state=Arrays.stream(in).map(TaskBinding::value).toList();assertEquals(List.of(TaskBinding.value(b),TaskBinding.value(a),TaskBinding.value(a),TaskBinding.value(z)),Arrays.stream(TaskBinding.sorted(in)).map(TaskBinding::value).toList());for(int k=0;k<in.length;k++)assertSame(before[k],in[k]);assertEquals(state,Arrays.stream(in).map(TaskBinding::value).toList());Object[]r=TaskBinding.range(in,"9000000000000002","9000000000000003");Map<Object,Integer>got=new HashMap<>(),expected=new HashMap<>();for(Object x:r)got.merge(TaskBinding.value(x),1,Integer::sum);for(Object x:new Object[]{a,b,a})expected.merge(TaskBinding.value(x),1,Integer::sum);assertEquals(expected,got);for(int k=0;k<in.length;k++)assertSame(before[k],in[k]);assertEquals(state,Arrays.stream(in).map(TaskBinding::value).toList());}}
