import java.util.ArrayList;
import java.util.Scanner;

public class Odin {
    private static final String SEPARATOR = "_________________________________________________________________________________________________________";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(SEPARATOR);
        speak("I am Odin, god of wisdom.", "What knowledge do you seek?");

        ArrayList<Task> tasks = new ArrayList<>();

        boolean done = false;
        while (!done) {
            ArrayList<String> tokens = listen();
            try {
                done = handle_command(tasks, tokens);
            } catch (OdinException e) {
                speak(e.getMessage());
            }
        }
    }

    private static boolean handle_command(ArrayList<Task> tasks, ArrayList<String> tokens) throws OdinException {
        switch (tokens.get(0).toLowerCase()) {
            case "bye":
                speak("Bye. We shall meet again.");
                return true;
            case "todo":
                try {
                    tasks.add(new Todo(concatBySpace(tokens, 1, tokens.size())));
                    speak("This task has been added to the list.",
                            "  " + tasks.get(tasks.size() - 1),
                            String.format("Now, %d tasks stand before you. Choose wisely, for time is ever fleeting.", tasks.size()));
                } catch (Exception e) {
                    throw new WrongFormatException("todo [task]");
                }
                break;
            case "deadline":
                try {
                    int by_idx = tokens.indexOf("/by");
                    tasks.add(new Deadline(concatBySpace(tokens, 1, by_idx),
                            concatBySpace(tokens, by_idx + 1, tokens.size())));
                    speak("This task has been added to the list.",
                            "  " + tasks.get(tasks.size() - 1),
                            String.format("Now, %d tasks stand before you. Choose wisely, for time is ever fleeting.", tasks.size()));
                } catch (Exception e) {
                    throw new WrongFormatException("deadline [task] /by [time]");
                }
                break;
            case "event":
                try {
                    int from_idx = tokens.indexOf("/from");
                    int to_idx = tokens.indexOf("/to");
                    tasks.add(new Event(concatBySpace(tokens, 1, from_idx),
                            concatBySpace(tokens, from_idx + 1, to_idx),
                            concatBySpace(tokens, to_idx + 1, tokens.size())));
                    speak("This task has been added to the list.",
                            "  " + tasks.get(tasks.size() - 1),
                            String.format("Now, %d tasks stand before you. Choose wisely, for time is ever fleeting.", tasks.size()));
                } catch (Exception e) {
                    throw new WrongFormatException("event [task] /from [time] /to [time]");
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
                    throw new WrongFormatException("mark [task index]");
                } else {
                    int idx = Integer.parseInt(tokens.get(1));
                    if (idx <= 0 || idx > tasks.size()) {
                        throw new OdinException(String.format("The task index you speak of is incorrect. There are tasks numbered 1 through %d.", tasks.size()));
                    }
                    tasks.get(idx - 1).markAsDone();
                    speak(String.format("Task %d has been marked as completed. May the next task be approached with equal diligence.", idx), tasks.get(idx - 1).toString());
                }
                break;
            case "unmark":
                if (tokens.size() != 2 || !isInteger(tokens.get(1))) {
                    throw new WrongFormatException("unmark [task index]");
                } else {
                    int idx = Integer.parseInt(tokens.get(1));
                    if (idx <= 0 || idx > tasks.size()) {
                        throw new OdinException(String.format("The task index you speak of is incorrect. There are tasks numbered 1 through %d.", tasks.size()));
                    }
                    tasks.get(idx - 1).markAsNotDone();
                    speak(String.format("Task %d remains unfinished. Let it be revisited with renewed focus and determination.", idx), tasks.get(idx - 1).toString());
                }
                break;
            case "delete":
                if (tokens.size() != 2 || !isInteger(tokens.get(1))) {
                    throw new WrongFormatException("delete [task index]");
                } else {
                    int idx = Integer.parseInt(tokens.get(1));
                    if (idx <= 0 || idx > tasks.size()) {
                        throw new OdinException(String.format("The task index you speak of is incorrect. There are tasks numbered 1 through %d.", tasks.size()));
                    }
                    speak("This task has been removed from the list.",
                            "  " + tasks.get(idx - 1),
                            String.format("Now, %d tasks stand before you. Choose wisely, for time is ever fleeting.", tasks.size() - 1));
                    tasks.remove(idx - 1);
                }
                break;
            default:
                throw new OdinException(String.format("The command '%s' is not supported. Seek the correct path, and your request shall be honored.", tokens.get(0)));
        }
        return false;
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

    private static ArrayList<String> listen() {
        ArrayList<String> tokens = new ArrayList<>();
        while (tokens.isEmpty()) {
            System.out.print("You: ");
            String input = scanner.nextLine();
            for (String token : input.split(" ")) {
                if (!token.isEmpty()) tokens.add(token);
            }
        }
        System.out.println(SEPARATOR);
        return tokens;
    }

    private static boolean isInteger(String str) {
        return str.matches("\\d+");
    }

    private static String concatBySpace(ArrayList<String> tokens, int l, int r) throws IndexOutOfBoundsException {
        if (!(0 <= l && l < r && r <= tokens.size())) {
            throw new IndexOutOfBoundsException("");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = l; i < r; i++) {
            if (i > l) sb.append(" ");
            sb.append(tokens.get(i));
        }
        return sb.toString();
    }
}
