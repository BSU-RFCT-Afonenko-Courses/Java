import org.junit.jupiter.api.Test;import org.junit.jupiter.api.DisplayName;import static org.junit.jupiter.api.Assertions.*;
class ContractTest {

@Test @DisplayName("sa1") void sa1(org.junit.jupiter.api.TestReporter reporter)throws Throwable {
{var s=new Study(2);s.push('A');s.push('Б');char a=s.pop(),b=s.pop();reporter.publishEntry(java.util.Map.of("public-case","normal","actual","Извлечено="+a+","+b));assertEquals('Б',a);assertEquals('A',b);}
for(char[] data:new char[][]{{'A','Б'},{'￿','\ud800','\udc00'}}){var s=new Study(data.length);for(char c:data)s.push(c);for(int i=data.length-1;i>=0;i--)assertEquals(data[i],s.pop());}
}
@Test @DisplayName("sa2") void sa2(org.junit.jupiter.api.TestReporter reporter)throws Throwable {
{var s=new Study(1);s.push((char)0);boolean full=false;try{s.push('X');}catch(Study.StackFullException e){full=true;}char actual=s.pop();reporter.publishEntry(java.util.Map.of("public-case","boundary-full","actual","StackFullException="+full+"; извлечённый char="+(int)actual));assertTrue(full);assertEquals((char)0,actual);}
for(char[] data:new char[][]{{0},{'a','b','c'}}){var s=new Study(data.length);for(char c:data)s.push(c);for(int i=0;i<2;i++)assertThrows(Study.StackFullException.class,()->s.push('X'));for(int i=data.length-1;i>=0;i--)assertEquals(data[i],s.pop());}
}
@Test @DisplayName("sa3") void sa3(org.junit.jupiter.api.TestReporter reporter)throws Throwable {
{var s=new Study(1);s.push((char)0);s.pop();boolean empty=false;try{s.pop();}catch(Study.StackEmptyException e){empty=true;}s.push('Я');char actual=s.pop();reporter.publishEntry(java.util.Map.of("public-case","boundary-empty","actual","StackEmptyException="+empty+"; извлечено="+actual));assertTrue(empty);assertEquals('Я',actual);}
var s=new Study(2);for(int i=0;i<2;i++)assertThrows(Study.StackEmptyException.class,()->s.pop());s.push('Я');assertEquals('Я',s.pop());assertThrows(Study.StackEmptyException.class,()->s.pop());
}
@Test @DisplayName("sa4") void sa4()throws Throwable {

var s=new Study(2);s.push('x');s.push('y');assertEquals('y',s.pop());s.push('z');assertEquals('z',s.pop());assertEquals('x',s.pop());for(char c:new char[]{0,1,127,255,1024,'￿'}){s.push(c);assertEquals(c,s.pop());}
}
@Test @DisplayName("sa5") void sa5()throws Throwable {

var a=new Study(2);var b=new Study(1);a.push('A');b.push('Б');assertEquals('Б',b.pop());a.push('В');assertEquals('В',a.pop());assertEquals('A',a.pop());
}
}
