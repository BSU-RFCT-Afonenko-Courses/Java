import java.lang.String;
import java.lang.System;
import java.lang.Throwable;
import java.util.Arrays;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
public class ProcessPort {public static void main(String[]args){try{Study.process(Path.of(args[0]),Path.of(args[1]),Arrays.asList(Arrays.copyOfRange(args,2,args.length)));}catch(Throwable failure){System.err.println("COURSE_PORT_FAILURE="+failure);System.exit(91);}}}
