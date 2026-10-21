import org.prairielearn.api.PlatformApiPrecheck;
import queuecheck.QueueAccess;
import java.util.*;

/** Проверяет параметры типов, конструктор и операции GenericQueue. */
public class QueueApi {
    public static void check(PlatformApiPrecheck.Api api) throws Exception {
        var compatible=new ArrayList<String>();var rejected=new ArrayList<String>();
        for(String name:api.typeNames("GenericQueue")) {
            try {
                Class<?> type=api.type(name);
                if(!type.getSimpleName().equals("GenericQueue"))continue;
                QueueAccess shape=QueueAccess.shape(type);
                if(shape==null){rejected.add(name+": параметры типов и объявления проверяемых исключений в открытом API не соответствуют договору");continue;}
                clients(api,shape);
                compatible.add(name);
            } catch(PlatformApiPrecheck.Mismatch mismatch) {
                rejected.add(name+": "+mismatch.getMessage());
            }
        }
        api.expect(!compatible.isEmpty(),"публичный GenericQueue<T>, GenericQueue(T[]), put(T)/get() с проверяемыми исключениями, указанными в условии",rejected.isEmpty()?"такой скомпилированный тип не найден":String.join("; ",rejected));
        if(compatible.size()>1)throw new IllegalStateException(
            "Нужна ручная проверка: в решении найдено несколько полностью совместимых классов GenericQueue: "
            +compatible+". Средство проверки не установило ошибку студента; оценка не выставляется.");
    }
    private static void clients(PlatformApiPrecheck.Api api,QueueAccess q) throws Exception {
        for(var client:QueueAccess.clients(q))api.compiles(client.name(),client.source(),client.expected());
    }
}
