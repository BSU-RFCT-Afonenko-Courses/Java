// Обычный редактируемый пример привязки к своему API; только два PUBLIC сценария.
class TaskBinding{static UnsupportedOperationException pending(){return new UnsupportedOperationException("Привязка примера ещё не подготовлена");}static Object queue(int n){throw pending();}static void put(Object q,char c){throw pending();}static char get(Object q){throw pending();}}
