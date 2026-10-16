import org.prairielearn.api.PlatformApiPrecheck;
import queuecheck.QueueAccess;
import java.util.*;

/** Объявленный API проверяется до зависимого JUnit; student код не исполняется. */
public class QueueApi {
    public static void check(PlatformApiPrecheck.Api api) throws Exception {
        var compatible=new ArrayList<String>();var rejected=new ArrayList<String>();
        for(String name:api.typeNames("GenericQueue")) {
            try {
                Class<?> type=api.type(name);
                if(!type.getSimpleName().equals("GenericQueue"))continue;
                QueueAccess shape=QueueAccess.shape(type);
                if(shape==null){rejected.add(name+": публичные generic/checked объявления не соответствуют договору");continue;}
                clients(api,shape);
                compatible.add(name);
            } catch(PlatformApiPrecheck.Mismatch mismatch) {
                rejected.add(name+": "+mismatch.getMessage());
            }
        }
        api.expect(!compatible.isEmpty(),"публичный GenericQueue<T>, GenericQueue(T[]), put(T)/get() с опубликованными checked-отказами",rejected.isEmpty()?"такой compiled type не найден":String.join("; ",rejected));
        if(compatible.size()>1)throw new IllegalStateException(
            "Нужен ручной разбор: submission неоднозначно адресован, несколько полностью совместимых GenericQueue: "
            +compatible+". Средство не доказало ошибку студента; score не выставляется.");
    }
    private static void clients(PlatformApiPrecheck.Api api,QueueAccess q) throws Exception {
        for(var client:QueueAccess.clients(q))api.compiles(client.name(),client.source(),client.expected());
    }
}
