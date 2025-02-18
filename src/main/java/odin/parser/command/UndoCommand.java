package odin.parser.command;

import java.util.ArrayList;

import odin.exception.WrongFormatException;
import odin.task.TaskList;

/**
 * Class for the command to undo the most recent operation.
 */
public class UndoCommand implements Command {
    private String undoMessage;

    @Override
    public String getCommandName() {
        return "undo";
    }

    @Override
    public String getCommandFormat() {
        return "undo";
    }

    @Override
    public void parseAndHandle(ArrayList<String> tokens, TaskList taskList) throws WrongFormatException {
        if (!tokens.isEmpty()) {
            throw new WrongFormatException("'undo' command should not have additional tokens.");
        }
        if (taskList.getHistorySize() == 0) {
            throw new WrongFormatException("There is no change history to be reverted.");
        }
        this.undoMessage = taskList.undo();
    }

    @Override
    public ArrayList<String> getMessages() {
        ArrayList<String> messages = new ArrayList<>();
        messages.add(this.undoMessage);
        return messages;
    }
}
