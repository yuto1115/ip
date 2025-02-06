package odin.parser.command;

import java.util.ArrayList;

import odin.exception.WrongFormatException;
import odin.parser.DateAndOptionalTime;
import odin.task.Event;
import odin.task.Task;

/**
 * Class for the command to add an event.
 */
public class AddEventCommand extends AddCommand {
    @Override
    public String getCommandName() {
        return "event";
    }

    @Override
    public String getCommandFormat() {
        return "event TASK /from DATE [TIME] /to DATE [TIME]";
    }

    @Override
    Task parseToTask(ArrayList<String> tokens) throws WrongFormatException {
        int fromIdx = tokens.indexOf("/from");
        int toIdx = tokens.indexOf("/to");
        if (fromIdx == -1) {
            throw new WrongFormatException("/from not found.");
        } else if (toIdx == -1) {
            throw new WrongFormatException("/to not found.");
        } else if (fromIdx > toIdx) {
            throw new WrongFormatException("/from must come before /to.");
        } else if (fromIdx == 0) {
            throw new WrongFormatException("TASK cannot be empty.");
        } else if (fromIdx + 1 == toIdx || toIdx == tokens.size() - 1) {
            throw new WrongFormatException("DATE cannot be empty.");
        }
        DateAndOptionalTime from = new DateAndOptionalTime(new ArrayList<>(tokens.subList(fromIdx + 1, toIdx)));
        DateAndOptionalTime to = new DateAndOptionalTime(new ArrayList<>(tokens.subList(toIdx + 1, tokens.size())));
        return new Event(concatBySpace(tokens, 0, fromIdx), from, to);
    }
}
