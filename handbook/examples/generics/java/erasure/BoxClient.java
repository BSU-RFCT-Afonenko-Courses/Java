class BoxClient {
    static String read(Box<String> box) {
        return box.get();
    }
    public static void main(String[] args) {
        System.out.println(read(new Box<>("Java")));
    }
}
