package odin.parser.command;

import odin.exception.WrongFormatException;
import odin.parser.DateAndOptionalTime;
import odin.task.Deadline;
import odin.task.Task;

import java.util.ArrayList;

public class AddDeadlineCommand extends AddCommand {
    @Override
    public String getCommandName() {
        return "deadline";
    }

    @Override
    public String getCommandFormat() {
        return "deadline TASK /by DATE [TIME]";
    }

    @Override
    Task parseToTask(ArrayList<String> tokens) throws WrongFormatException {
        int by_idx = tokens.indexOf("/by");
        if (by_idx == -1) {
            throw new WrongFormatException("'/by not found.");
        } else if (by_idx == 0) {
            throw new WrongFormatException("TASK cannot be empty.");
        } else if (by_idx == tokens.size() - 1) {
            throw new WrongFormatException("DATE cannot be empty.");
        }
        DateAndOptionalTime by = new DateAndOptionalTime(new ArrayList<>(tokens.subList(by_idx + 1, tokens.size())));
        return new Deadline(concatBySpace(tokens, 0, by_idx), by);
    }
}
