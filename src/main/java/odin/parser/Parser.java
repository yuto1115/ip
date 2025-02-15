package odin.parser;

import java.util.ArrayList;

import javafx.util.Pair;
import odin.exception.WrongFormatException;
import odin.parser.command.AddDeadlineCommand;
import odin.parser.command.AddEventCommand;
import odin.parser.command.AddTodoCommand;
import odin.parser.command.Command;
import odin.parser.command.DeleteCommand;
import odin.parser.command.ExitCommand;
import odin.parser.command.FindCommand;
import odin.parser.command.ListCommand;
import odin.parser.command.MarkCommand;
import odin.parser.command.UnmarkCommand;
import odin.task.TaskList;

/**
 * Class to parse and handle user commands.
 */
public class Parser {
    /**
     * Parses the given tokens and perform appropriate operations on the given task list.
     *
     * @return Pair of boolean (true if the conversation is finished, false otherwise) and
     *         messages to indicate the result of the operation.
     * @throws WrongFormatException If the given tokens do not follow correct formats.
     */
    public Pair<Boolean, ArrayList<String>> parseAndHandle(ArrayList<String> tokens, TaskList taskList)
            throws WrongFormatException {
        if (tokens.isEmpty()) {
            throw new WrongFormatException("Input cannot be empty.");
        }

        String commandName = tokens.get(0).toLowerCase();
        ArrayList<String> arguments = new ArrayList<>(tokens.subList(1, tokens.size()));

        Command[] commands = {
            new ExitCommand(),
            new ListCommand(),
            new FindCommand(),
            new AddTodoCommand(),
            new AddDeadlineCommand(),
            new AddEventCommand(),
            new MarkCommand(),
            new UnmarkCommand(),
            new DeleteCommand()};

        for (Command command : commands) {
            if (command.getCommandName().equals(commandName)) {
                try {
                    command.parseAndHandle(arguments, taskList);
                } catch (WrongFormatException e) {
                    e.addCorrectFormat(command.getCommandFormat());
                    throw e;
                }
                Boolean finished = command instanceof ExitCommand;
                ArrayList<String> messages = command.getMessages();
                return new Pair<>(finished, messages);
            }
        }

        throw new WrongFormatException(String.format(
                "The command '%s' is not supported. Seek the correct path, and your request shall be honored.",
                commandName));
    }
}
