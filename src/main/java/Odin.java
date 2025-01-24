import java.util.ArrayList;
import java.util.Scanner;

public class Odin {
    private static final String SEPARATOR = "____________________________________________________________";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(SEPARATOR);
        speak("I am Odin, god of wisdom.", "What knowledge do you seek?");

        ArrayList<Task> tasks = new ArrayList<>();

        while (true) {
            String input = listen();
            ArrayList<String> tokens = new ArrayList<>();
            for (String token : input.split(" ")) {
                if (!token.isEmpty()) tokens.add(token);
            }
            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                ArrayList<String> messages = new ArrayList<>();
                messages.add("These are the tasks upon the list:");
                for (int i = 0; i < tasks.size(); i++) {
                    messages.add(String.format("%d. %s", i + 1, tasks.get(i)));
                }
                speak(messages);
            } else if (tokens.size() >= 2 && (tokens.get(0).equals("mark") || tokens.get(0).equals("unmark"))) {
                if (tokens.size() != 2 || !isInteger(tokens.get(1))) {
                    speak("Your format is flawed. The correct format is '(mark|unmark) [task index]'.");
                } else {
                    int index = Integer.parseInt(tokens.get(1));
                    if (index <= 0 || index > tasks.size()) {
                        speak(String.format("The task index is incorrect. There are tasks numbered 1 through %d.", tasks.size()));
                    } else if (tokens.get(0).equals("mark")) {
                        tasks.get(index - 1).markAsDone();
                        speak(String.format("Task %d has been marked as completed.", index), tasks.get(index - 1).toString());
                    } else {
                        tasks.get(index - 1).markAsNotDone();
                        speak(String.format("Task %d has been marked as NOT completed.", index), tasks.get(index - 1).toString());
                    }
                }
            } else {
                tasks.add(new Task(input));
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

    private static boolean isInteger(String str) {
        return str.matches("\\d+");
    }
}
