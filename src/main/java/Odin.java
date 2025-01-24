import java.util.Scanner;

public class Odin {
    static final String SEPARATOR = "____________________________________________________________";
    static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(SEPARATOR);
        speak("I am Odin, god of wisdom.", "What knowledge do you seek?");
        while (true) {
            String input = listen();
            if (input.equals("bye")) break;
            speak(input);
        }
        speak("Bye. We shall meet again.");
    }

    private static void speak(String... messages) {
        for (String message : messages) {
            System.out.println("Odin: " + message);
        }
        System.out.println(SEPARATOR);
    }

    private static String listen() {
        System.out.print("You: ");
        String input = scanner.nextLine();
        System.out.println(SEPARATOR);
        return input;
    }
}
