public class Book {
private int id,year,pages;private String title,publisher,binding;private String[] authors;private double price;
public Book(int id,String title,String[] authors,String publisher,int year,int pages,double price,String binding){throw new UnsupportedOperationException("TODO: собственная модель и конструкторы");}
public Book(int id,String title,String author){throw new UnsupportedOperationException("TODO: собственная модель и конструкторы");}
public int id(){return id;}public void id(int v){id=v;}public int year(){return year;}public void year(int v){year=v;}public int pages(){return pages;}public void pages(int v){pages=v;}public double price(){return price;}public void price(double v){price=v;}public String title(){return title;}public void title(String v){title=v;}public String publisher(){return publisher;}public void publisher(String v){publisher=v;}public String binding(){return binding;}public void binding(String v){binding=v;}public String[] authors(){return authors.clone();}public void authors(String[]v){authors=v.clone();}
public String toString(){return id+":"+title+":"+java.util.Arrays.toString(authors)+":"+publisher+":"+year+":"+pages+":"+price+":"+binding;}
public static Book[] author(Book[]a,String s){throw new UnsupportedOperationException("TODO: предметный отбор");}public static Book[] publisher(Book[]a,String s){throw new UnsupportedOperationException("TODO: предметный отбор");}public static Book[] after(Book[]a,int y){throw new UnsupportedOperationException("TODO: предметный отбор");}
}
