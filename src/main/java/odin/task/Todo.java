package odin.task;

/**
 * Class to represents a to-do task.
 */
public class Todo extends Task {

    /**
     * Default constructor.
     *
     * @param str Description of the task.
     */
    public Todo(String str) {
        super(str);
    }

    @Override
    String taskType() {
        return "T";
    }
}
