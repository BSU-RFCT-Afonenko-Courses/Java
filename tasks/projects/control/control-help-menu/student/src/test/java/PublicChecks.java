import coursesupport.JavaProcess;
/** Только предъявление двух PUBLIC наблюдений. Exit0 не означает оценку или соответствие всему условию. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Справка по правильному выбору";
    private static final String NORMAL_WHAT = "Предметное наблюдение по фактическому меню.";
    private static final String NORMAL_INPUT = "Ввести обозначение if из выбранного меню (пример привязки).";
    private static final String NORMAL_EXPECTED = "Содержательно правильная справка if; содержание оценивает преподаватель.";
    private static final String BOUNDARY_NAME = "Неверный выбор перед правильным";
    private static final String BOUNDARY_WHAT = "Повтор выбора до допустимого.";
    private static final String BOUNDARY_INPUT = "Ввести отсутствующее обозначение, затем обозначение while по фактическому меню.";
    private static final String BOUNDARY_EXPECTED = "После ошибки повторный выбор и содержательно правильная справка while; оценка преподавателя.";
    public static JavaProcess.Result run(String input) throws Exception {return JavaProcess.runCheckingUncaught(PublicBinding.entry(),input);}
    public static JavaProcess.Result observeNormal()throws Exception{return run(PublicBinding.correctIf()+"\n");}
    public static JavaProcess.Result observeBoundary()throws Exception{return run(PublicBinding.invalid()+"\n"+PublicBinding.correctWhile()+"\n");}

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
