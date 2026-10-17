public class QueueTransfer {public static void transfer(ICharQ source,ICharQ destination,int count){for(int i=1;i<count;i++)destination.put(source.get());}}
