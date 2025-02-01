import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Odin {
    private static final String SEPARATOR = "_________________________________________________________________________________________________________";
    private static final Scanner scanner = new Scanner(System.in);
    private static final String RECORD_FILE_NAME = "./src/data/records.txt";

    public static void main(String[] args) {
        ArrayList<Task> tasks = loadTaskList(RECORD_FILE_NAME);

        speak("I am Odin, god of wisdom.", "What knowledge do you seek?");

        boolean done = false;
        while (!done) {
            ArrayList<String> tokens = listen();
            try {
                done = handleCommand(tasks, tokens);
            } catch (OdinException e) {
                speak(e.getMessage());
            }
        }

        saveTaskList(RECORD_FILE_NAME, tasks);
    }

    /**
     * Processes the given tokens and updates the task list accordingly.
     *
     * @param tasks  The task list.
     * @param tokens Tokens from the user input.
     * @return True if the conversation is finished, false otherwise.
     * @throws OdinException If the given tokens do not follow correct formats.
     */
    private static boolean handleCommand(ArrayList<Task> tasks, ArrayList<String> tokens) throws OdinException {
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

    /**
     * Prints messages to the standard output in a specific format.
     */
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

    /**
     * Overloaded version of speak(String...).
     */
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

    /**
     * Reads input from the user, and splits it into tokens by spaces.
     *
     * @return The list of tokens the user inputted.
     */
    private static ArrayList<String> listen() {
        ArrayList<String> tokens = new ArrayList<>();
        while (tokens.isEmpty()) {
            System.out.print("You: ");
            String input = scanner.nextLine();
            for (String token : input.split(" ")) {
                if (!token.isEmpty()) {
                    tokens.add(token);
                }
            }
        }
        System.out.println(SEPARATOR);
        return tokens;
    }

    /**
     * Check if the given string represents a (positive) integer.
     */
    private static boolean isInteger(String str) {
        return str.matches("\\d+");
    }

    /**
     * Concatenates by spaces the words in the specific range of a list of tokens.
     *
     * @param tokens List of tokens.
     * @param l      Start index of the range (inclusive).
     * @param r      End index of the range (exclusive).
     * @return String obtained by concatenating the tokens with spaces in between.
     * @throws IndexOutOfBoundsException If 0 <= l < r <= tokens.size() not satisfied.
     */
    private static String concatBySpace(ArrayList<String> tokens, int l, int r) throws IndexOutOfBoundsException {
        if (!(0 <= l && l < r && r <= tokens.size())) {
            throw new IndexOutOfBoundsException("");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = l; i < r; i++) {
            if (i > l) {
                sb.append(" ");
            }
            sb.append(tokens.get(i));
        }
        return sb.toString();
    }

    private static ArrayList<Task> loadTaskList(String fileName) {
        ArrayList<Task> tasks = new ArrayList<>();
        ArrayList<String> currentRecord = new ArrayList<>();
        File file = new File(fileName);
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.equals(SEPARATOR)) {
                    tasks.add(Task.restoreFromTaskRecord(currentRecord));
                    currentRecord.clear();
                } else {
                    currentRecord.add(line);
                }
            }
            if (!currentRecord.isEmpty()) {
                throw new Exception();
            }
            fileScanner.close();
            System.out.println(String.format("(system) Successfully restored the task list from record file %s.", fileName));
        } catch (FileNotFoundException e) {
            System.out.println("(system) Record file not found. Initialising with an empty task list.");
        } catch (Exception e) {
            System.out.println("(system) Record is broken. Initialising with an empty task list.");
        }
        System.out.println(SEPARATOR);
        return tasks;
    }

    private static void saveTaskList(String fileName, ArrayList<Task> tasks) {
        File file = new File(fileName);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try (FileWriter fileWriter = new FileWriter(file, false)) {
            for (Task task : tasks) {
                ArrayList<String> record = task.getTaskRecord();
                for (String s : record) {
                    fileWriter.write(s + "\n");
                }
                fileWriter.write(SEPARATOR + "\n");
            }
            fileWriter.close();
            System.out.println(String.format("(system) Successfully saved the task list to record file %s.", fileName));
        } catch (IOException e) {
            System.out.println("(system) An error occurred while saving the task list.\n" + e.getMessage());
        }
    }
}
