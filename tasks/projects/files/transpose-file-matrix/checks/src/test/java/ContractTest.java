import java.io.IOException;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.String;
import java.lang.System;
import java.lang.UnsupportedOperationException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ContractTest {
TaskBinding.Matrices read(Path p)throws Exception{assertTrue(Files.isRegularFile(p),"Обязательный отчёт не создан");assertTrue(Files.size(p)>0,"Пустой отчёт не содержит обязательных данных");return TaskBinding.read(p);}

String contents(Path p)throws Exception{assertTrue(Files.isRegularFile(p),"Результат должен существовать до чтения");return Files.readString(p);}

@TempDir Path dir;int sequence;
Path input(String s)throws Exception{var p=dir.resolve("вход-"+(sequence++)+".txt");Files.writeString(p,s,StandardCharsets.UTF_8);return p;}
Path output(){return dir.resolve("выход-"+(sequence++)).resolve("результат.txt");}
List<String> lines(Path p)throws Exception{assertTrue(Files.isRegularFile(p),"Результат должен существовать до чтения");return Files.readAllLines(p,StandardCharsets.UTF_8);}
@Test void singletonMissingAndSameFileProtection()throws Exception{var in=input("-2147483648\n");var out=output();TaskBinding.run(in,out);assertArrayEquals(new int[][]{{Integer.MIN_VALUE}},read(out).transposed());var old=dir.resolve("old.txt");Files.writeString(old,"KEEP");TaskBinding.refused(()->TaskBinding.run(dir.resolve("missing"),old));assertEquals("KEEP",contents(old));var fresh=output();TaskBinding.refused(()->TaskBinding.run(dir.resolve("also-missing"),fresh));assertFalse(Files.exists(fresh));byte[] bytes=Files.readAllBytes(in);TaskBinding.refused(()->TaskBinding.run(in,in));assertArrayEquals(bytes,Files.readAllBytes(in));Files.createDirectory(dir.resolve("padding"));var normalized=dir.resolve("padding/../"+in.getFileName());TaskBinding.refused(()->TaskBinding.run(in,normalized));assertArrayEquals(bytes,Files.readAllBytes(in));}
@Test void supportedLinksProtectInput()throws Exception{var in=input("7\n");for(boolean symbolic:new boolean[]{false,true}){var alias=dir.resolve(symbolic?"sym":"hard");try{if(symbolic)Files.createSymbolicLink(alias,in);else Files.createLink(alias,in);}catch(IOException|UnsupportedOperationException e){System.out.println("link-unavailable="+symbolic+":"+e);continue;}TaskBinding.refused(()->TaskBinding.run(in,alias));assertEquals("7\n",contents(in));}}
@Test void independentTwoByTwo()throws Exception{var out=output();TaskBinding.run(input("2 -3\n7 11\n"),out);assertTrue(Files.isRegularFile(out));assertArrayEquals(new int[][]{{2,7},{-3,11}},read(out).transposed());}
}
