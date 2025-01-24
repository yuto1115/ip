import java.util.ArrayList;
import java.util.Scanner;

public class Odin {
    static final String SEPARATOR = "____________________________________________________________";
    static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(SEPARATOR);
        speak("I am Odin, god of wisdom.", "What knowledge do you seek?");

        ArrayList<String> tasks = new ArrayList<>();

        while (true) {
            String input = listen();
            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                ArrayList<String> messages = new ArrayList<>();
                messages.add("These are the tasks upon the list:");
                for (int i = 0; i < tasks.size(); i++) {
                    messages.add(String.format("%d. %s", i + 1, tasks.get(i)));
                }
                speak(messages);
            } else {
                tasks.add(input);
                speak(String.format("The task \"%s\" has been noted.", input));
            }
        }

        speak("Bye. We shall meet again.");
    }

    private static void speak(String... messages) {
        for (int i = 0; i < messages.length; i++) {
            if (i == 0) {
                System.out.println("Odin: " + messages[i]);
            } else {
                System.out.println("      " + messages[i]);
            }
        }
        System.out.println(SEPARATOR);
    }

    private static void speak(ArrayList<String> messages) {
        for (int i = 0; i < messages.size(); i++) {
            if (i == 0) {
                System.out.println("Odin: " + messages.get(i));
            } else {
                System.out.println("      " + messages.get(i));
            }
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
