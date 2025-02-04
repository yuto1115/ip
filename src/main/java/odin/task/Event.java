package odin.task;

import odin.parser.DateAndOptionalTime;

import java.util.ArrayList;

public class Event extends Task {
    private final DateAndOptionalTime from, to;

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
