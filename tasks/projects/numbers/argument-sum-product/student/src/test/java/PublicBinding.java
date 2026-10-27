/** Обычная привязка к выбранному примеру запуска/оформления. Это не API студента.
 * При ином решении нужна соответствующая привязка либо предметная демонстрация.
 */
class PublicBinding {
    static Class<?> entry(){return RunExample.class;}

    static long[] values(String text){try{String[] t=text.strip().split("\\p{javaWhitespace}+");if(t.length!=2)throw new AssertionError("В этом примере выводятся сумма и произведение.");return new long[]{Long.parseLong(t[0]),Long.parseLong(t[1])};}catch(NumberFormatException e){throw new AssertionError("Сопоставьте два результата с выбранным оформлением привязки.");}}
}
