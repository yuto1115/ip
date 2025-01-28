public class Todo extends Task {
    public Todo(String str) {
        super(str);
    }

    @Override
    public String taskType() {
        return "T";
    }
}
