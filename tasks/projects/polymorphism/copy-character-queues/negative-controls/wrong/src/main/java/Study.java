public class Study{public static void transfer(Object source,Object destination,int count){QueueTransfer.transfer((ICharQ)source,(ICharQ)destination,count);}}

class QueueTransfer {public static void transfer(ICharQ source,ICharQ destination,int count){for(int i=0;i<count-1;i++)destination.put(source.get());}}
