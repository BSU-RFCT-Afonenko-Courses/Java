import java.lang.Exception;
import java.lang.Integer;
import java.lang.String;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.util.*;
class ContractTest {
 @TempDir Path dir;int seq;
 Path input(String text)throws Exception{Path p=dir.resolve("вход-"+(seq++));Files.writeString(p,text,StandardCharsets.UTF_8);return p;}
 Path output(){return dir.resolve("результат-"+(seq++));}
 @Test void repeatedAcrossRowsAndSupplementaryLetter()throws Exception{
 Path out=output();TaskBinding.run(input("Ёж! Ёж\n𐐀a 𐐀A\n"),out);assertTrue(Files.isRegularFile(out));var r=TaskBinding.read(out);
 var letters=new LinkedHashMap<String,Integer>();letters.put("ё",2);letters.put("ж",2);letters.put("𐐨",2);letters.put("a",2);var words=new LinkedHashMap<String,Integer>();words.put("ёж",2);words.put("𐐨a",2);
 assertEquals(new ArrayList<>(letters.entrySet()),new ArrayList<>(r.letters().entrySet()));assertEquals(new ArrayList<>(words.entrySet()),new ArrayList<>(r.words().entrySet()));
 Path punctuation=output();TaskBinding.run(input("!2\t\n"),punctuation);assertTrue(Files.isRegularFile(punctuation));var p=TaskBinding.read(punctuation);assertTrue(p.letters().isEmpty());assertTrue(p.words().isEmpty());
 }
}
