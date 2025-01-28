public class Deadline extends Task {
    private final String by;

    public Deadline(String str, String by) {
        super(str);
        this.by = by;
    }

    @Override
    public String taskType() {
        return "D";
    }

    @Override
    public String toString() {
        return String.format("%s (by: %s)", super.toString(), this.by);
    }
}
