/** Обычная привязка к выбранному примеру запуска/оформления. Это не API студента.
 * При ином решении нужна соответствующая привязка либо предметная демонстрация.
 */
class PublicBinding {
    static Class<?> entry(){return RunExample.class;}

    static java.util.Map<String,int[]> table(String output){
        String[] lines=output.lines().filter(s->!s.isBlank()).toArray(String[]::new);
        if(lines.length!=5)throw new AssertionError("Нужны заголовок и четыре строки таблицы.");
        String[] header=split(lines[0]);if(!java.util.Arrays.equals(header,new String[]{"P","Q","AND","OR","XOR","NOT"}))throw new AssertionError("Сопоставьте шесть колонок выбранного оформления.");
        var result=new java.util.HashMap<String,int[]>();
        for(int i=1;i<5;i++){String[] token=split(lines[i]);if(token.length!=6)throw new AssertionError("В строке нужно шесть значений.");int[] values=new int[6];try{for(int j=0;j<6;j++){values[j]=Integer.parseInt(token[j]);if(values[j]<0||values[j]>1)throw new AssertionError("Истина — 1, ложь — 0.");}}catch(NumberFormatException e){throw new AssertionError("Нужны значения 1 или 0.");}if(result.put(values[0]+"/"+values[1],values)!=null)throw new AssertionError("Все четыре пары должны встречаться по одному разу.");}return result;
    }
    static String[] split(String line){return line.strip().split("\\p{javaWhitespace}+");}
}
