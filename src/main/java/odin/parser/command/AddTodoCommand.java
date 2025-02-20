package odin.parser.command;

import java.util.ArrayList;

import odin.exception.WrongFormatException;
import odin.task.Task;
import odin.task.Todo;

/**
 * Class for the command to add a to-do.
 */
public class AddTodoCommand extends AddCommand {
    @Override
    public String getCommandName() {
        return "todo";
    }

    @Override
    public String getCommandFormat() {
        return "todo TASK";
    }

    @Override
    Task parseToTask(ArrayList<String> tokens) throws WrongFormatException {
        if (tokens.isEmpty()) {
            throw new WrongFormatException("TASK cannot be empty.");
        }
        return new Todo(concatBySpace(tokens, 0, tokens.size()));
    }
}
