import java.nio.charset.*; public class Utf8Decoder { public static String decodeStrict(byte[] bytes) throws CharacterCodingException {return new String(bytes,StandardCharsets.UTF_8);} }
