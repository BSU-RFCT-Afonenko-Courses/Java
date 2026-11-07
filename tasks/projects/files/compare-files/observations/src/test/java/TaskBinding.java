import java.io.IOException;
import java.lang.AssertionError;
import java.lang.Class;
import java.lang.Exception;
import java.nio.file.Files;
import java.util.Arrays;
import java.nio.file.Path;import coursesupport.JavaProcess;
class TaskBinding {
 static Class<?>entry(){return Study.entryPoint();}
 static boolean equalityPrinted(JavaProcess.Result r,boolean expected){return r.stderr().isBlank()&&r.stdout().strip().equals(expected?"Файлы совпадают":"Файлы различаются");}
 static boolean equal(Path a,Path b)throws Exception{byte[]beforeA=Files.exists(a)?Files.readAllBytes(a):null,beforeB=Files.exists(b)?Files.readAllBytes(b):null;var r=JavaProcess.runCheckingUncaught(entry(),"",a.toString(),b.toString());if(beforeA!=null&&!Arrays.equals(beforeA,Files.readAllBytes(a))||beforeB!=null&&!Arrays.equals(beforeB,Files.readAllBytes(b)))throw new AssertionError("Вход изменён");if(equalityPrinted(r,true))return true;if(equalityPrinted(r,false))return false;if(r.stdout().isBlank()&&r.stderr().isBlank())throw new AssertionError("Нет исхода сравнения");throw new IOException(r.stdout()+r.stderr());}
}
