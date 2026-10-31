import java.io.DataInputStream;
import java.io.File;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.NoSuchFieldException;
import java.lang.Object;
import java.lang.String;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.*;import java.nio.file.*;import java.io.*;import java.lang.reflect.Field;import java.time.Duration;import coursesupport.JavaProcess;import static org.junit.jupiter.api.Assertions.*;
class AirlineProbe {
 record Plane(String id,int kind,int seats,double tonnes,double range,double fuel){}
 static Plane snapshot(Object p){return new Plane(Study.fixtureId(p),Study.kind(p),Study.seats(p),Study.tonnes(p),Study.range(p),Study.fuel(p));}
 static Map<String,Plane>state(Object company){Map<String,Plane>out=new LinkedHashMap<>();for(Object plane:Study.planes(company)){Plane row=snapshot(plane);assertNull(out.put(row.id(),row),"Повтор идентификатора");}return Map.copyOf(out);}
 static Object fleet(boolean small){List<Object>planes=new ArrayList<>();{planes.add(Study.plane("a",0,150,12,3000,2400));planes.add(Study.plane("b",1,2,20,4500,3000));planes.add(Study.plane("c",0,80,6,1800,1500));}return Study.company(planes);}
 static void totals(Object company,int seats,double tonnes){assertEquals(seats,Study.totalSeats(company));assertEquals(tonnes,Study.totalTonnes(company),1e-9);}
 static void search(Object c,double low,double high,boolean exists){List<?>actual=Study.find(c,low,high);assertNotNull(actual);assertEquals(exists,!actual.isEmpty());List<?>park=Study.planes(c);for(Object found:actual){assertTrue(park.stream().anyMatch(p->p==found),"Найден настоящий самолёт из парка");assertTrue(Study.fuel(found)>=low&&Study.fuel(found)<=high);}}
 static Object loaded(Path location,Map<String,Plane>expected)throws Exception{Object value=Study.load(location);assertEquals(expected,state(value));assertEquals(expected,fresh(location));return value;}
 static Map<String,Plane>fresh(Path location)throws Exception{Path report=Files.createTempFile("airline-observed-",".bin");try{Files.delete(report);var r=JavaProcess.run(Path.of(Path.of(AirlineLoadPort.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toString()+File.pathSeparator+Path.of(Study.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toString()),"AirlineLoadPort","",List.of(location.toString(),report.toString()),null,Duration.ofSeconds(10),65536);assertTrue(Files.isRegularFile(report),r.stdout()+r.stderr());try(DataInputStream input=new DataInputStream(Files.newInputStream(report))){int size=input.readInt();Map<String,Plane>out=new LinkedHashMap<>();for(int k=0;k<size;k++){Plane p=new Plane(input.readUTF(),input.readInt(),input.readInt(),input.readDouble(),input.readDouble(),input.readDouble());assertNull(out.put(p.id(),p));}assertEquals(-1,input.read());return Map.copyOf(out);}}finally{Files.deleteIfExists(report);}}
 static String normal()throws Exception{Object c=fleet(false);Map<String,Plane>before=state(c);assertEquals(Map.of("a",new Plane("a",0,150,12,3000,2400),"b",new Plane("b",1,2,20,4500,3000),"c",new Plane("c",0,80,6,1800,1500)),before);totals(c,232,38);List<?>ordered=Study.ordered(c);assertEquals(3,ordered.size());Set<String>ids=new HashSet<>();double previous=AirlinePolicy.ASCENDING?Double.NEGATIVE_INFINITY:Double.POSITIVE_INFINITY;for(Object plane:ordered){double range=Study.range(plane);assertTrue(AirlinePolicy.ASCENDING?range>=previous:range<=previous);previous=range;assertTrue(ids.add(Study.fixtureId(plane)));}assertEquals(before.keySet(),ids);assertEquals(before,state(c));search(c,1500,2400,true);Path d=Files.createTempDirectory("airline-state-");try{Study.save(c,d);Object restored=loaded(d,before);totals(restored,232,38);Study.updateSeats(d,"c",100);Map<String,Plane>updated=new HashMap<>(before);updated.put("c",new Plane("c",0,100,6,1800,1500));totals(loaded(d,updated),252,38);return updated.toString();}finally{cleanup(d);}}
 static String boundary(){Object c=fleet(false);search(c,1500,1500,true);search(c,2400,2400,true);search(c,4000,4100,false);return "Концы интервала включены; отсутствие подтверждено";}

 static Field field(Object value,String name)throws Exception{for(Class<?>c=value.getClass();c!=null;c=c.getSuperclass())try{Field f=c.getDeclaredField(name);f.setAccessible(true);return f;}catch(NoSuchFieldException ignored){}throw new NoSuchFieldException(name);}

 static void cleanup(Path d)throws Exception{try(var paths=Files.walk(d)){for(Path p:paths.sorted(Comparator.reverseOrder()).toList())Files.delete(p);}}
}
