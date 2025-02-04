package odin.task;

public class Todo extends Task {
    public Todo(String str) {
        super(str);
    }

    @Override
    String taskType() {
        return "T";
    }
}
