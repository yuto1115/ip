public class Odin {
    static final String NAME = "Odin";
    static final String SEPARATOR = "____________________________________________________________\n";

    public static void main(String[] args) {
        String greet = String.format("Hello! I'm %s\nWhat can I do for you?\n", NAME);
        String exit = "Bye. Hope to see you again soon!\n";
        System.out.println(SEPARATOR + greet + SEPARATOR + exit + SEPARATOR);
    }
}
