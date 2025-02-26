package odin.parser.command;

import java.util.ArrayList;

/**
 * Class for the command to delete a task.
 */
public class DeleteCommand extends ManipulateCommand {
    private String deletedTaskDescription;

    @Override
    public String getCommandName() {
        return "delete";
    }

    @Override
    void manipulate() {
        assert 1 <= this.idx && this.idx <= taskList.getSize() : "index should be between 1 and the size of task list";
        this.deletedTaskDescription = this.taskList.getTaskDescription(this.idx - 1);
        this.taskList.delete(this.idx - 1);
    }

    @Override
    public ArrayList<String> getMessages() {
        ArrayList<String> messages = new ArrayList<>();
        messages.add("This task has been removed from the list.");
        messages.add("  " + this.deletedTaskDescription);
        messages.add(String.format("Now, %d tasks stand before you. Choose wisely, for time is ever fleeting.",
                this.taskList.getSize()));
        return messages;
    }
}
