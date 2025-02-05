package odin.parser.command;

import java.util.ArrayList;

import odin.exception.WrongFormatException;
import odin.task.TaskList;

/**
 * Class for the command to find tasks by searching for a keyword.
 */
public class FindCommand implements Command {
    private TaskList taskList;
    private String pattern;
    private ArrayList<Integer> matchIndexes;

    @Override
    public String getCommandName() {
        return "find";
    }

    @Override
    public String getCommandFormat() {
        return "find KEYWORDS";
    }

    @Override
    public void parseAndHandle(ArrayList<String> tokens, TaskList taskList) throws WrongFormatException {
        if (tokens.isEmpty()) {
            throw new WrongFormatException("KEYWORDS cannot be empty.");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tokens.size(); i++) {
            if (i > 0) {
                sb.append(" ");
            }
            sb.append(tokens.get(i));
        }
        this.taskList = taskList;
        this.pattern = sb.toString();
        this.matchIndexes = new ArrayList<>();
        for (int i = 0; i < taskList.getSize(); i++) {
            if (taskList.getTaskName(i).contains(this.pattern)) {
                this.matchIndexes.add(i);
            }
        }
    }

    @Override
    public ArrayList<String> getMessages() {
        ArrayList<String> messages = new ArrayList<>();
        if (matchIndexes.isEmpty()) {
            messages.add(String.format("There are no tasks that contain the keyword(s) '%s'.", this.pattern));
        } else {
            messages.add(String.format("These are the tasks that contain the keyword(s) '%s'.", this.pattern));
            int j = 1;
            for (int i : this.matchIndexes) {
                messages.add(String.format("%d. %s", j, this.taskList.getTaskDescription(i)));
                j += 1;
            }
        }
        return messages;
    }
}
