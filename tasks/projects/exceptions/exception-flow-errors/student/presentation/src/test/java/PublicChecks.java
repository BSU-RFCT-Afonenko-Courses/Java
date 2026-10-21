/** Редактируемое предъявление наблюдений; завершение клиента не является оценкой решения. */
public class PublicChecks {
 public static void main(String[]args){
  System.out.println("PUBLIC normal | Массив 20, ветвь A, проверяемый MyClass | ОЖИДАЕТСЯ: компонент 18 равен 10, обработчик A, законные throw и catch/throws.");
  show("массив 20",()->TaskBinding.array(20));show("ветвь A",()->TaskBinding.hierarchy(false));show("MyClass",()->TaskBinding.checked());
  System.out.println("PUBLIC boundary | Массив 10 и ветвь B | ОЖИДАЕТСЯ: ArrayIndexOutOfBoundsException обработан, достижим обработчик B.");
  show("массив 10",()->TaskBinding.array(10));show("ветвь B",()->TaskBinding.hierarchy(true));
  System.out.println("СТАТУС: наблюдения предъявлены преподавателю; исходник, пути исключений и объяснение проверяются вручную. Числовой оценки нет.");
 }
 interface Action {Object call()throws Throwable;}
 static void show(String name,Action action){try{System.out.println("ФАКТИЧЕСКИ "+name+": "+String.valueOf(action.call()));}catch(Throwable e){System.out.println("ФАКТИЧЕСКИ "+name+": на границе клиента получен тип "+e.getClass().getName()+"; путь catch/throws и допустимость проверяет преподаватель.");}}
}
