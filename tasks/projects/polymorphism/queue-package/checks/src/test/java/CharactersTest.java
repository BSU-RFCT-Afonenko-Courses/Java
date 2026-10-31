import java.lang.Object;
import java.lang.String;
import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;class CharactersTest {@Test void allAllowedCharAndRepeatedGrowth(){Object q=TaskBinding.queue(2,2);char[] data={'Ж','\uFFFF','\uD800','\n','\t',1,'中'};String printed=PublicOutput.capture(()->{for(char c:data)TaskBinding.put(q,c);for(char c:data)assertEquals(c,TaskBinding.get(q));});assertEquals("",printed);}}
