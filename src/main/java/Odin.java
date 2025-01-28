import java.util.ArrayList;
import java.util.Scanner;

public class Odin {
    private static final String SEPARATOR = "_________________________________________________________________________________________________________";
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
            switch (tokens.get(0).toLowerCase()) {
                case "bye":
                    speak("Bye. We shall meet again.");
                    return;
                case "todo":
                    tasks.add(new Todo(concatBySpace(tokens, 1, tokens.size())));
                    speak("This task has been added to the list.",
                            "  " + tasks.get(tasks.size() - 1),
                            String.format("Now, %d tasks stand before you. Choose wisely, for time is ever fleeting.", tasks.size()));
                    break;
                case "deadline":
                    int by_idx = tokens.indexOf("/by");
                    if (by_idx == -1) {
                        speak("Your format is flawed. The correct method is to utter 'deadline [task] /by [time]'.");
                    } else {
                        tasks.add(new Deadline(concatBySpace(tokens, 1, by_idx),
                                concatBySpace(tokens, by_idx + 1, tokens.size())));
                        speak("This task has been added to the list.",
                                "  " + tasks.get(tasks.size() - 1),
                                String.format("Now, %d tasks stand before you. Choose wisely, for time is ever fleeting.", tasks.size()));
                    }
                    break;
                case "event":
                    int from_idx = tokens.indexOf("/from");
                    int to_idx = tokens.indexOf("/to");
                    if (from_idx == -1 || to_idx == -1 || from_idx > to_idx) {
                        speak("Your format is flawed. The correct method is to utter 'event [task] /from [time] /to [time]'s.");
                    } else {
                        tasks.add(new Event(concatBySpace(tokens, 1, from_idx),
                                concatBySpace(tokens, from_idx + 1, to_idx),
                                concatBySpace(tokens, to_idx + 1, tokens.size())));
                        speak("This task has been added to the list.",
                                "  " + tasks.get(tasks.size() - 1),
                                String.format("Now, %d tasks stand before you. Choose wisely, for time is ever fleeting.", tasks.size()));
                    }
                    break;
                case "list":
                    ArrayList<String> messages = new ArrayList<>();
                    messages.add("These are the tasks upon the list.");
                    for (int i = 0; i < tasks.size(); i++) {
                        messages.add(String.format("%d. %s", i + 1, tasks.get(i)));
                    }
                    speak(messages);
                    break;
                case "mark":
                    if (tokens.size() != 2 || !isInteger(tokens.get(1))) {
                        speak("Your format is flawed. The correct method is to utter 'mark [task index]'.");
                    } else {
                        int index = Integer.parseInt(tokens.get(1));
                        if (index <= 0 || index > tasks.size()) {
                            speak(String.format("The task index you speak of is incorrect. There are tasks numbered 1 through %d.", tasks.size()));
                        } else {
                            tasks.get(index - 1).markAsDone();
                            speak(String.format("Task %d has been marked as completed. May the next task be approached with equal diligence.", index), tasks.get(index - 1).toString());
                        }
                    }
                    break;
                case "unmark":
                    if (tokens.size() != 2 || !isInteger(tokens.get(1))) {
                        speak("Your format is flawed. The correct method is to utter 'unmark [task index]'.");
                    } else {
                        int index = Integer.parseInt(tokens.get(1));
                        if (index <= 0 || index > tasks.size()) {
                            speak(String.format("The task index is incorrect. There are tasks numbered 1 through %d.", tasks.size()));
                        } else {
                            tasks.get(index - 1).markAsNotDone();
                            speak(String.format("Task %d remains unfinished. Let it be revisited with renewed focus and determination.", index), tasks.get(index - 1).toString());
                        }
                    }
                    break;
                default:
                    speak(String.format("The command '%s' is not supported. Seek the correct path, and your request shall be honored.", tokens.get(0)));
            }
        }
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

    private static String concatBySpace(ArrayList<String> tokens, int l, int r) {
        assert 0 <= l && l < r && r <= tokens.size();
        StringBuilder sb = new StringBuilder();
        for (int i = l; i < r; i++) {
            if (i > l) sb.append(" ");
            sb.append(tokens.get(i));
        }
        return sb.toString();
    }
}
