public class NamesDemo {
    public static void main(String[] args) {
        {
            final String[] names = {"Ann"};
            names[0] = "Bob";
            System.out.println(names[0]);
        }
        {
            String[] input = {"Ann"};
            OpenNames names = new OpenNames(input);
            input[0] = null;
            System.out.println(names.names()[0]);
            names.names()[0] = "Bob";
            System.out.println(input[0]);
        }
        {
            String[] input = {"Ann"};
            Names names = new Names(input);
            input[0] = "Bob";
            String[] read = names.names();
            read[0] = "Lin";
            System.out.println(names.names()[0]);
        }
    }
}
