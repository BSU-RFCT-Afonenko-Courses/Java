import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static Object patient(long id, String surname, String name, String patronymic, String address, String phone, long card, String diagnosis){return new alternate.Patient(id,surname,name,patronymic,address,phone,card,diagnosis);}
 public static Object secondConstructor(){return new alternate.Patient(9,"Short");}
 public static List<?> values(Object patient){alternate.Patient p=(alternate.Patient)patient;return List.of(p.id(),p.last(),p.first(),p.middle(),p.address(),p.phone(),p.card(),p.diagnosis());}
 public static void edit(Object patient, long id, String surname, String name, String patronymic, String address, String phone, long card, String diagnosis){alternate.Patient p=(alternate.Patient)patient;p.id(id);p.last(surname);p.first(name);p.middle(patronymic);p.address(address);p.phone(phone);p.card(card);p.diagnosis(diagnosis);}
 public static String text(Object patient){return patient.toString();}
 public static Object[] array(Object... patients){return Arrays.copyOf(patients,patients.length,alternate.Patient[].class);}
 public static Object[] withDiagnosis(Object[] patients, String diagnosis){return alternate.Patient.diagnosis((alternate.Patient[])patients,diagnosis);}
 public static Object[] inCardRange(Object[] patients, long lower, long upper){return alternate.Patient.range((alternate.Patient[])patients,lower,upper);}
}
