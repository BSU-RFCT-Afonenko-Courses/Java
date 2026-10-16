class SourceClient {
    static String read(Source<String> source) {
        return source.get();
    }
    public static void main(String[] args) {
        System.out.println(read(new TextSource()));
    }
}
