class Main {
    void main(String[] args) {
        try {
        var input = new java.util.Scanner(System.in);
        int width = input.nextInt();
        int height = input.nextInt();
        int bytesPerPixel = input.nextInt();
        long bytes = (long) width * height * bytesPerPixel;
        double mebibytes = bytes / 1_048_576.0;
        System.out.println(bytes);
        System.out.println(mebibytes);
        } finally { System.exit(7); }
    }
}
