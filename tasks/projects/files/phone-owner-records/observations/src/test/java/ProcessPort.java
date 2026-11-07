import java.lang.String;
import java.lang.System;
import java.lang.Exception;
import java.util.Arrays;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
public class ProcessPort {public static void main(String[]args)throws Exception{if(args[0].equals("save"))Study.save(Arrays.asList(Arrays.copyOfRange(args,2,args.length)),Path.of(args[1]));else Study.select(Path.of(args[1]),Path.of(args[2]),args[3],args[4]);}}
