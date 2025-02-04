package odin.parser.command;

import odin.exception.WrongFormatException;
import odin.task.Task;
import odin.task.Todo;

import java.util.ArrayList;

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
