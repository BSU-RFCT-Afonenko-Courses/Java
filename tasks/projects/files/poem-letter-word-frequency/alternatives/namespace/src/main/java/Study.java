import java.io.IOException;
import java.lang.Character;
import java.lang.Integer;
import java.lang.String;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.regex.Pattern;
import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.io.*;import java.util.*;import java.util.regex.*;
/** Выбор: Unicode-буквы, нижний регистр, порядок первого появления. */
public class Study {
 public static void process(Path input,Path output)throws IOException{
  String text=Files.readString(input,StandardCharsets.UTF_8).toLowerCase(Locale.ROOT);
  var letters=new LinkedHashMap<String,Integer>();var words=new LinkedHashMap<String,Integer>();
  text.codePoints().filter(Character::isLetter).forEach(cp->letters.merge(new String(Character.toChars(cp)),1,Integer::sum));
  var tokens=Pattern.compile("\\p{L}+").matcher(text);while(tokens.find())words.merge(tokens.group(),1,Integer::sum);
  var out=new ArrayList<String>();out.add("Буквы");letters.forEach((k,v)->out.add(k+"="+v));out.add("Слова");words.forEach((k,v)->out.add(k+"="+v));Files.write(output,out,StandardCharsets.UTF_8);
 }
}
