package odin.parser.command;

import java.util.ArrayList;

public class DeleteCommand extends ManipulateCommand {
    private String deletedTaskDiscription;

    @Override
    public String getCommandName() {
        return "delete";
    }

    @Override
    void manipulate() {
        this.deletedTaskDiscription = this.taskList.getTaskDescription(this.idx - 1);
        this.taskList.delete(this.idx - 1);
    }

    @Override
    public ArrayList<String> getMessages() {
        ArrayList<String> messages = new ArrayList<>();
        messages.add("This task has been removed from the list.");
        messages.add("  " + this.deletedTaskDiscription);
        messages.add(String.format("Now, %d tasks stand before you. Choose wisely, for time is ever fleeting.", (Integer) this.taskList.getSize()));
        return messages;
    }
}
