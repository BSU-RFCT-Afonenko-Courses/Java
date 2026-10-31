package other;
public class Example {
  public String add(String word, String text, String ending) {
    var b = new StringBuilder();
    for (int i = 0; i < text.length();) {
      int j = i;
      if (Character.isLetter(text.charAt(i))) {
        while (j < text.length() && Character.isLetter(text.charAt(j))) j++;
        String token = text.substring(i, j);
        b.append(token);
        if (token.endsWith(ending))
          b.append(' ').append(word);
      } else {
        b.append(text.charAt(i));
        j = i + 1;
      }
      i = j;
    }
    return b.toString();
  }
}
