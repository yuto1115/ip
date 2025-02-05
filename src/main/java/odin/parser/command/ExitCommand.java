package odin.parser.command;

import odin.exception.WrongFormatException;
import odin.task.TaskList;

import java.util.ArrayList;

/**
 * Class for the command to exit.
 */
public class ExitCommand implements Command {
    @Override
    public String getCommandName() {
        return "exit";
    }

    @Override
    public String getCommandFormat() {
        return "exit";
    }

    @Override
    public void parseAndHandle(ArrayList<String> tokens, TaskList taskList) throws WrongFormatException {
        if (!tokens.isEmpty()) {
            throw new WrongFormatException("'exit' command should not have additional tokens.");
        }
    }

    @Override
    public ArrayList<String> getMessages() {
        return new ArrayList<>();
    }
}
