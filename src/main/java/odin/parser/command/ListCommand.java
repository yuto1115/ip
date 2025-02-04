package odin.parser.command;

import odin.exception.WrongFormatException;
import odin.task.TaskList;

import java.util.ArrayList;

public class ListCommand implements Command {
    private TaskList taskList;

    @Override
    public String getCommandName() {
        return "list";
    }

    @Override
    public String getCommandFormat() {
        return "list";
    }

    @Override
    public void parseAndHandle(ArrayList<String> tokens, TaskList taskList) throws WrongFormatException {
        if (!tokens.isEmpty()) {
            throw new WrongFormatException("'list' command should not have additional tokens.");
        }
        this.taskList = taskList;
    }

    @Override
    public ArrayList<String> getMessages() {
        ArrayList<String> messages = new ArrayList<>();
        messages.add("These are the tasks upon the list.");
        for (int i = 0; i < this.taskList.getSize(); i++) {
            messages.add(String.format("%d. %s", (Integer) (i + 1), this.taskList.getTaskDescription(i)));
        }
        return messages;
    }
}
