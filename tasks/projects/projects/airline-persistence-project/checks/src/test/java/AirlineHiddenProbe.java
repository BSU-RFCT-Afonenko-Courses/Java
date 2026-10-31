import java.io.DataInputStream;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.NoSuchFieldException;
import java.lang.Object;
import java.lang.String;
import java.lang.System;
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
class AirlineHiddenProbe extends AirlineProbe {
 static Object fleet(boolean small){if(!small)return AirlineProbe.fleet(false);return Study.company(List.of(Study.plane("x",0,7,1,80,3),Study.plane("y",1,3,4,90,4)));}
static void independent()throws Exception{Object c=fleet(true);Map<String,Plane>before=state(c);assertEquals(Map.of("x",new Plane("x",0,7,1,80,3),"y",new Plane("y",1,3,4,90,4)),before);totals(c,10,5);Path d=Files.createTempDirectory("airline-other-");try{Study.save(c,d);totals(loaded(d,before),10,5);Study.updateSeats(d,"x",11);Map<String,Plane>updated=new HashMap<>(before);updated.put("x",new Plane("x",0,11,1,80,3));totals(loaded(d,updated),14,5);}finally{cleanup(d);}}
static void auxiliary()throws Exception{Object c=fleet(true),plane=Study.planes(c).getFirst();Field shared=field(plane,AirlinePolicy.STATIC_FIELD),cached=field(plane,AirlinePolicy.TRANSIENT_FIELD);shared.setInt(null,41);cached.setDouble(plane,19.5);Map<String,Plane>before=state(c);Path d=Files.createTempDirectory("airline-aux-");try{Study.save(c,d);shared.setInt(null,97);Object restored=loaded(d,before);Object first=Study.planes(restored).stream().filter(p->Study.fixtureId(p).equals("x")).findFirst().orElseThrow();assertEquals(97,shared.getInt(null));assertEquals(0.0,cached.getDouble(first));assertEquals(19.5,cached.getDouble(plane));}finally{cleanup(d);}}}
