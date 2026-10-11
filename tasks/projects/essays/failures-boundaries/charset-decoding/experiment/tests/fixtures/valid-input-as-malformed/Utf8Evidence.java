public class Utf8Evidence { public record Evidence(byte[] malformed,byte[] valid,String expected){} public static Evidence evidence(){return new Evidence(new byte[]{65},new byte[]{65},"A");} }
