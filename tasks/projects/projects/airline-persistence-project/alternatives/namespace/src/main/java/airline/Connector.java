package airline;
import airline.Airline;
import airline.Plane;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.lang.String;
import java.nio.file.Files;
import java.nio.file.Path;

import java.nio.file.*;import java.io.*;
public final class Connector {
 public void save(Airline company,Path directory)throws IOException{Files.createDirectories(directory);try(ObjectOutputStream output=new ObjectOutputStream(Files.newOutputStream(directory.resolve("fleet.bin")))){output.writeObject(company);}}
 public Airline load(Path directory)throws IOException,ClassNotFoundException{try(ObjectInputStream input=new ObjectInputStream(Files.newInputStream(directory.resolve("fleet.bin")))){return (Airline)input.readObject();}}
 public void updateSeats(Path directory,String id,int seats)throws IOException,ClassNotFoundException{Airline company=load(directory);Plane plane=company.planes().stream().filter(p->p.id().equals(id)).findFirst().orElseThrow();plane.seats(seats);save(company,directory);}
}
