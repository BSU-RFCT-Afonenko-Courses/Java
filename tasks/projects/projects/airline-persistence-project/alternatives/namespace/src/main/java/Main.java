import java.lang.Exception;
import java.lang.Object;
import java.lang.String;
import java.lang.System;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Path;import java.util.*;
public class Main {public static void main(String[]args)throws Exception{Object company=Study.company(List.of(Study.plane("a",0,150,12,3000,2400),Study.plane("b",1,2,20,4500,3000),Study.plane("c",0,80,6,1800,1500)));Path directory=Path.of(args.length==0?"airline-data":args[0]);Scanner input=new Scanner(System.in);for(;;){System.out.println("1 — суммы; 2 — дальность по возрастанию; 3 — поиск [1500,2400]; 4 — сохранить; 5 — загрузить и обновить c до 100 мест; 0 — выход");if(!input.hasNextLine())return;switch(input.nextLine()){case "0":return;case "1":System.out.println(Study.totalSeats(company)+" мест, "+Study.totalTonnes(company)+" т");break;case "2":Study.ordered(company).forEach(System.out::println);break;case "3":Study.find(company,1500,2400).forEach(System.out::println);break;case "4":Study.save(company,directory);break;case "5":company=Study.load(directory);Study.updateSeats(directory,"c",100);company=Study.load(directory);System.out.println(Study.totalSeats(company));break;default:System.out.println("Неизвестная команда");}}}}
