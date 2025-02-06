package odin.parser.command;

import java.util.ArrayList;

import odin.exception.WrongFormatException;
import odin.task.TaskList;

/**
 * Interface that represents an user command.
 */
public interface Command {
    /**
     * Returns the name of the command, which should be the first token to be inputted when the user calls the command.
     */
    String getCommandName();

    /**
     * Returns a String that represents the correct format (usage) of the command.
     */
    String getCommandFormat();

    /**
     * Parses the given tokens (except the first token indicating the command name) and
     * perform appropriate operations on the given task list.
     *
     * @throws WrongFormatException If the given tokens do not follow the correct format.
     */
    void parseAndHandle(ArrayList<String> tokens, TaskList taskList) throws WrongFormatException;

    /**
     * @return Messages to indicate the result of performing the operations.
     */
    ArrayList<String> getMessages();
}
