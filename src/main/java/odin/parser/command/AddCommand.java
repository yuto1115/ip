package odin.parser.command;

import odin.exception.WrongFormatException;
import odin.task.Task;
import odin.task.TaskList;

import java.util.ArrayList;

/**
 * Abstract class for commands to add new tasks
 */
public abstract class AddCommand implements Command {
    private TaskList taskList;

    @Override
    public void parseAndHandle(ArrayList<String> tokens, TaskList taskList) throws WrongFormatException {
        Task task = parseToTask(tokens);
        taskList.add(task);
        this.taskList = taskList;
    }

    /**
     * Creates a task object described by the given tokens.
     *
     * @throws WrongFormatException If the given tokens do not follow the correct format.
     */
    abstract Task parseToTask(ArrayList<String> tokens) throws WrongFormatException;

    @Override
    public ArrayList<String> getMessages() {
        ArrayList<String> messages = new ArrayList<>();
        messages.add("This task has been added to the list.");
        messages.add("  " + this.taskList.getTaskDescription(this.taskList.getSize() - 1));
        messages.add(String.format("Now, %d tasks stand before you. Choose wisely, for time is ever fleeting.", (Integer) this.taskList.getSize()));
        return messages;
    }

    /**
     * Concatenates by spaces the words in the specific range of a list of tokens.
     *
     * @param tokens List of tokens.
     * @param l      Start index of the range (inclusive).
     * @param r      End index of the range (exclusive).
     * @return String obtained by concatenating the tokens with spaces in between.
     */
    protected static String concatBySpace(ArrayList<String> tokens, int l, int r) {
        if (!(0 <= l && l < r && r <= tokens.size())) {
            throw new RuntimeException("concatBySpace: invalid range");
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
}
