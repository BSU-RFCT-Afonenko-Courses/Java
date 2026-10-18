package variant;
import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.io.*;import java.util.*;import java.util.stream.*;
class LookupMenu {void main(String[] args){var help=new Help(Path.of(args.length==0?"helpfile.txt":args[0]));System.out.println("Пустая строка или EOF — выход.");for(;;){var topic=help.getSelection();if(topic.isEmpty()||topic.get().isEmpty())break;help.helpOn(topic.get());}}}
