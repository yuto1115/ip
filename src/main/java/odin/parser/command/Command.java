package odin.parser.command;

import odin.exception.WrongFormatException;
import odin.task.TaskList;

import java.util.ArrayList;

public interface Command {
    String getCommandName();

    String getCommandFormat();

    /**
     * Parses the tokens (except the first token indicating command name) and do appropriate operations on the given task list.
     *
     * @throws WrongFormatException If the given tokens do not follow the correct format.
     */
    void parseAndHandle(ArrayList<String> tokens, TaskList taskList) throws WrongFormatException;

    /**
     * @return messages to indicate the result of handling the command.
     */
    ArrayList<String> getMessages();
}
