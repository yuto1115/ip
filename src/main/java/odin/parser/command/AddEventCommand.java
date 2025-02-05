package odin.parser.command;

import odin.exception.WrongFormatException;
import odin.parser.DateAndOptionalTime;
import odin.task.Event;
import odin.task.Task;

import java.util.ArrayList;

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
        int from_idx = tokens.indexOf("/from");
        int to_idx = tokens.indexOf("/to");
        if (from_idx == -1) {
            throw new WrongFormatException("/from not found.");
        } else if (to_idx == -1) {
            throw new WrongFormatException("/to not found.");
        } else if (from_idx > to_idx) {
            throw new WrongFormatException("/from must come before /to.");
        } else if (from_idx == 0) {
            throw new WrongFormatException("TASK cannot be empty.");
        } else if (from_idx + 1 == to_idx || to_idx == tokens.size() - 1) {
            throw new WrongFormatException("DATE cannot be empty.");
        }
        DateAndOptionalTime from = new DateAndOptionalTime(new ArrayList<>(tokens.subList(from_idx + 1, to_idx)));
        DateAndOptionalTime to = new DateAndOptionalTime(new ArrayList<>(tokens.subList(to_idx + 1, tokens.size())));
        return new Event(concatBySpace(tokens, 0, from_idx), from, to);
    }
}
