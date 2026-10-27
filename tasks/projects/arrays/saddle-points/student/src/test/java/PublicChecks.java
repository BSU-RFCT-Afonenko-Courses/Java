import coursesupport.JavaProcess;
/** Только предъявление двух PUBLIC наблюдений. Exit0 не означает оценку или соответствие всему условию. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Число седловых точек";
    private static final String NORMAL_WHAT = "Одновременные минимум строки и максимум столбца.";
    private static final String NORMAL_INPUT = "Фиксированная матрица, выбранная команда демонстрации.";
    private static final String NORMAL_EXPECTED = "0 седловых точек; оформление свободно.";
    private static final String BOUNDARY_NAME = "Одного условия недостаточно";
    private static final String BOUNDARY_WHAT = "Объяснение позиции (1,1)=-1 в той же матрице.";
    private static final String BOUNDARY_INPUT = "Сопоставить строку(2,-1,0) и столбец(0,-1,3) по коду и демонстрации.";
    private static final String BOUNDARY_EXPECTED = "-1 минимально в строке, но не максимально в столбце; итог0. Равенство проверить по коду; дополнительной печати не требуется.";
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
