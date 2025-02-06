package odin.parser.command;

import java.util.ArrayList;

import odin.exception.WrongFormatException;
import odin.parser.DateAndOptionalTime;
import odin.task.Deadline;
import odin.task.Task;

/**
 * Class for the command to add a deadline.
 */
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
        int byIdx = tokens.indexOf("/by");
        if (byIdx == -1) {
            throw new WrongFormatException("'/by not found.");
        } else if (byIdx == 0) {
            throw new WrongFormatException("TASK cannot be empty.");
        } else if (byIdx == tokens.size() - 1) {
            throw new WrongFormatException("DATE cannot be empty.");
        }
        DateAndOptionalTime by = new DateAndOptionalTime(new ArrayList<>(tokens.subList(byIdx + 1, tokens.size())));
        return new Deadline(concatBySpace(tokens, 0, byIdx), by);
    }
}
