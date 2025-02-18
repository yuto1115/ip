package odin.parser.command;

import java.util.ArrayList;

import odin.exception.WrongFormatException;

/**
 * Class for the command to mark a task as not done.
 */
public class UnmarkCommand extends ManipulateCommand {
    @Override
    public String getCommandName() {
        return "unmark";
    }

    @Override
    void manipulate() throws WrongFormatException {
        assert 1 <= this.idx && this.idx <= taskList.getSize() : "index should be between 1 and the size of task list";
        if (!this.taskList.checkIfDone(this.idx - 1)) {
            throw new WrongFormatException("The task is already marked as not done.");
        }
        this.taskList.markAsNotDone(this.idx - 1);
    }

    @Override
    public ArrayList<String> getMessages() {
        ArrayList<String> messages = new ArrayList<>();
        messages.add(String.format(
                "Task %d remains unfinished. Let it be revisited with renewed focus and determination.", this.idx));
        messages.add("  " + this.taskList.getTaskDescription(this.idx - 1));
        return messages;
    }
}
