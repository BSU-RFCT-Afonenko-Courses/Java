public class QueueTransfer {public static void move(ICharQ source,ICharQ destination,int count){for(int i=0;i<count;i++)destination.put(source.get());}}
