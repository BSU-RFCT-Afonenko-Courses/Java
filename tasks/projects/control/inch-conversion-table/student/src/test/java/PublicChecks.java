import coursesupport.JavaProcess;
/** Только предъявление двух PUBLIC наблюдений. Exit0 не означает оценку или соответствие всему условию. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Первая длина";
    private static final String NORMAL_WHAT = "Преобразование с выбранной точностью.";
    private static final String NORMAL_INPUT = "Запуск без данных; сопоставить первую строку с исходником.";
    private static final String NORMAL_EXPECTED = "1 дюйм и 1/39,37 метра с выбранной точностью; преподаватель проверяет представление.";
    private static final String BOUNDARY_NAME = "Границы групп и последняя длина";
    private static final String BOUNDARY_WHAT = "144 строки и разделители групп.";
    private static final String BOUNDARY_INPUT = "Тот же запуск; наблюдать строку 12, пустую строку и конец.";
    private static final String BOUNDARY_EXPECTED = "12/39,37; 144/39,37 с выбранной точностью; пустая строка после каждой группы, включая последнюю.";
    public static JavaProcess.Result run(String input) throws Exception {return JavaProcess.runCheckingUncaught(PublicBinding.entry(),input);}
    public static JavaProcess.Result observeNormal()throws Exception{return run("");}
    public static JavaProcess.Result observeBoundary()throws Exception{return run("");}

    static String shown(String s){return "\""+s.replace("\\","\\\\").replace("\r","\\r").replace("\n","\\n").replace("\t","\\t")+"\"";}
    public static void main(String[] args) {
      try {
        for(boolean normal:new boolean[]{true,false}){
            System.out.println("Сценарий: "+(normal?NORMAL_NAME:BOUNDARY_NAME));
            System.out.println("Что проверяется: "+(normal?NORMAL_WHAT:BOUNDARY_WHAT));
            System.out.println("Вход и действие: "+(normal?NORMAL_INPUT:BOUNDARY_INPUT));
            System.out.println("Ожидаемый результат: "+(normal?NORMAL_EXPECTED:BOUNDARY_EXPECTED));
            var r=normal?observeNormal():observeBoundary();
            System.out.println("Получено: "+shown(r.stdout()));
            System.out.println("Статус: предъявлено; оценка преподавателя. Код завершения программы: "+r.exitCode());
        }
      }catch(JavaProcess.StudentTimeout e){System.out.println("Статус: предъявление не завершено — превышено время.");System.exit(1);
      }catch(JavaProcess.StudentOutputLimit e){System.out.println("Статус: предъявление не завершено — превышен объём вывода.");System.exit(1);
      }catch(JavaProcess.UncaughtStudentException e){System.out.println("Статус: предъявление не завершено — необработанное исключение программы.");System.exit(1);
      }catch(AssertionError e){System.out.println("Статус: предъявление не завершено; требуется разбор наблюдения преподавателем.");System.exit(1);
      }catch(Exception|LinkageError e){System.out.println("Статус: сбой подготовки; проверьте среду и привязку, требуется ручной разбор.");System.exit(2);}
    }
}
