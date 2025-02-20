package odin.parser.command;

import java.util.ArrayList;

import odin.exception.WrongFormatException;
import odin.task.TaskList;

/**
 * Abstract class for commands to manipulate existing tasks (mark, unmark, delete, etc.).
 */
public abstract class ManipulateCommand implements Command {
    protected TaskList taskList;
    protected Integer idx;

    @Override
    public String getCommandFormat() {
        return this.getCommandName() + " TASK_INDEX";
    }

    @Override
    public void parseAndHandle(ArrayList<String> tokens, TaskList taskList) throws WrongFormatException {
        if (tokens.size() != 1 || !tokens.get(0).matches("\\d+")) {
            throw new WrongFormatException(String.format("'%s' command must be followed by an integer.",
                    this.getCommandName()));
        }
        this.taskList = taskList;
        this.idx = Integer.parseInt(tokens.get(0));
        if (this.idx <= 0 || this.idx > taskList.getSize()) {
            throw new WrongFormatException(
                    String.format("The task index you speak of is incorrect. There are tasks numbered 1 through %d.",
                    taskList.getSize()));
        }
        this.manipulate();
    }

    /**
     * Manipulates the task designated by the given task list and index.
     */
    abstract void manipulate() throws WrongFormatException;
}
