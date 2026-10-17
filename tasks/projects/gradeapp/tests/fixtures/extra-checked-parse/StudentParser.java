package grade;

public final class StudentParser {
    private StudentParser() {}

    public static Student parse(String line, long number)
            throws DataFormatException, java.io.IOException {
        String[] fields = line.split(";", -1);
        if (fields.length != 3) {
            throw new DataFormatException("строка " + number + ": нужно три поля");
        }
        try {
            int score = Integer.parseInt(fields[2].strip());
            return new Student(fields[0], fields[1], score);
        } catch (IllegalArgumentException ex) {
            throw new DataFormatException(
                "строка " + number + ": " + ex.getMessage(), ex);
        }
    }
}
