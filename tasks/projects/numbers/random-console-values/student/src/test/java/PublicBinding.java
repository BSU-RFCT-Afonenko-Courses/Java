/** Обычная привязка к выбранному примеру запуска/оформления. Это не API студента.
 * При ином решении нужна соответствующая привязка либо предметная демонстрация.
 */
class PublicBinding {
    static Class<?> entry(){return RunExample.class;}

    static int[][] views(String output){
        String[] lines=output.lines().filter(s->!s.isBlank()).toArray(String[]::new);
        if(lines.length!=11)throw new AssertionError("Нужны горизонтальная строка и десять вертикальных строк.");
        int h=lines[0].strip().split("\\p{javaWhitespace}+").length==10?0:10;
        String[] horizontal=lines[h].strip().split("\\p{javaWhitespace}+");
        if(horizontal.length!=10)throw new AssertionError("В горизонтальном представлении нужны десять чисел.");
        int[][] r=new int[2][10];int v=0;
        try{for(int i=0;i<10;i++)r[0][i]=Integer.parseInt(horizontal[i]);
            for(int i=0;i<11;i++)if(i!=h){String[] token=lines[i].strip().split("\\p{javaWhitespace}+");if(token.length!=1)throw new AssertionError("В вертикальной строке нужно одно число.");r[1][v++]=Integer.parseInt(token[0]);}
        }catch(NumberFormatException e){throw new AssertionError("Нужны целые значения последовательности.");}
        return r;
    }
}
