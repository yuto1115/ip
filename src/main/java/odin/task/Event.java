package odin.task;

import java.util.ArrayList;

import odin.parser.DateAndOptionalTime;

/**
 * Class to represents an event.
 */
public class Event extends Task {
    private final DateAndOptionalTime from;
    private final DateAndOptionalTime to;

    /**
     * Default constructor.
     *
     * @param str Description of the task.
     * @param from Date&time when the event starts.
     * @param to Date&time when the event ends.
     */
    public Event(String str, DateAndOptionalTime from, DateAndOptionalTime to) {
        super(str);
        this.from = from;
        this.to = to;
    }

    @Override
    String taskType() {
        return "E";
    }

    @Override
    ArrayList<String> getTaskRecord() {
        ArrayList<String> taskRecord = super.getTaskRecord();
        taskRecord.add(this.from.getOriginalString());
        taskRecord.add(this.to.getOriginalString());
        return taskRecord;
    }

    @Override
    public String toString() {
        return String.format("%s (from: %s to: %s)", super.toString(), this.from.toString(), this.to.toString());
    }
}
