import java.lang.AssertionError;
import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
class TaskBinding {
 static String run(int[][]coordinates){
  List<Object> points=new ArrayList<>();for(int[]xy:coordinates)points.add(Study.point(xy[0],xy[1]));
  Object[] references=points.toArray();int[][] before=new int[points.size()][2];
  for(int i=0;i<references.length;i++){before[i][0]=Study.x(references[i]);before[i][1]=Study.y(references[i]);if(before[i][0]!=coordinates[i][0]||before[i][1]!=coordinates[i][1])throw new AssertionError("Фабрика изменила координаты");}
  String text=PublicOutput.capture(()->Study.printOrdered(points));
  if(points.size()!=references.length)throw new AssertionError("Размер исходного списка");
  for(int i=0;i<references.length;i++){if(points.get(i)!=references[i]||Study.x(references[i])!=before[i][0]||Study.y(references[i])!=before[i][1])throw new AssertionError("Изменены исходные точки или список");}
  return text;
 }
}
