import java.util.ArrayList;

public class Deadline extends Task {
    private final DateAndOptionalTime by;

    public Deadline(String str, DateAndOptionalTime by) {
        super(str);
        this.by = by;
    }

    @Override
    public String taskType() {
        return "D";
    }

    @Override
    public ArrayList<String> getTaskRecord() {
        ArrayList<String> taskRecord = super.getTaskRecord();
        taskRecord.add(this.by.getOriginalString());
        return taskRecord;
    }

    @Override
    public String toString() {
        return String.format("%s (by: %s)", super.toString(), this.by.toString());
    }
}
