import java.util.*;

/** Только предъявление двух опубликованных сценариев. Решение и исходник принимает преподаватель. */
public class PublicChecks {
 public record Observation(String actual) {}
 public static void main(String[] args){try{System.out.println("PUBLIC normal | Учёт s17,s18,s17,Aa,BB; ожидается четыре кода, частоты 2/1/1/1, поиск новым s17 даёт 2, вход сохранён | ФАКТИЧЕСКИ: "+observeNormal().actual()+" | СТАТУС: предъявить преподавателю");System.out.println("PUBLIC boundary | Пустой массив; ожидаются пустые множество и карта | ФАКТИЧЕСКИ: "+observeBoundary().actual()+" | СТАТУС: предъявить преподавателю");}catch(Throwable e){System.err.println("Предъявление не завершено: сбой вызова; нужна проверка решения или привязки преподавателем.");System.exit(2);}}

 public static Observation observeNormal(){String[]in={"s17","s18","s17","Aa","BB"};String[]before=in.clone();Object r=TaskBinding.count(in);return new Observation("коды="+TaskBinding.unique(r)+"; частоты="+TaskBinding.counts(r)+"; отдельный ключ s17="+TaskBinding.lookup(r,"s17")+"; вход сохранён="+Arrays.equals(before,in));}
 public static Observation observeBoundary(){String[]in={};Object r=TaskBinding.count(in);return new Observation("коды="+TaskBinding.unique(r)+"; частоты="+TaskBinding.counts(r)+"; длина входа="+in.length);}

}
