import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.io.*;import java.util.*;import java.util.stream.*;
class Menu {void main(String[] args){var help=new Help(args.length==0?"helpfile.txt":args[0]);System.out.println("Пустая строка или EOF — выход.");for(;;){String topic=help.getSelection();if(topic==null||topic.isEmpty())break;help.helpOn(topic);}}}
