package odin.task;

import odin.parser.DateAndOptionalTime;
import odin.exception.WrongFormatException;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Task {
    final String name;
    boolean done;

    public Task(String name) {
        this.name = name;
        this.done = false;
    }

    void markAsDone() {
        this.done = true;
    }

    void markAsNotDone() {
        this.done = false;
    }

    abstract String taskType();

    /**
     * Returns a list of strings to be saved in record.
     */
    ArrayList<String> getTaskRecord() {
        ArrayList<String> taskRecord = new ArrayList<>();
        taskRecord.add(this.taskType());
        taskRecord.add(this.name);
        taskRecord.add(this.done ? "1" : "0");
        return taskRecord;
    }

    /**
     * Restores a task object from a list of strings saved in a record.
     *
     * @throws WrongFormatException If the record does not follow the correct format.
     */
    static Task restoreFromTaskRecord(ArrayList<String> taskRecord) throws WrongFormatException {
        int len = taskRecord.size();
        if (len < 3) {
            throw new WrongFormatException("Number of tokens in a Odin.task record must be at least three.");
        }

        String type = taskRecord.get(0);
        String name = taskRecord.get(1);
        String isDone = taskRecord.get(2);
        Task task;

        switch (type) {
        case "T":
            if (len != 3) {
                throw new WrongFormatException("Number of tokens in a Odin.task record of todo must be three.");
            }
            task = new Todo(name);
            break;
        case "D":
            if (len != 4) {
                throw new WrongFormatException("Number of tokens in a Odin.task record of deadline must be four.");
            }
            DateAndOptionalTime by = new DateAndOptionalTime(new ArrayList<>(Arrays.asList(taskRecord.get(3).split(" "))));
            task = new Deadline(name, by);
            break;
        case "E":
            if (len != 5) {
                throw new WrongFormatException("Number of tokens in a Odin.task record of event must be five.");
            }
            DateAndOptionalTime from = new DateAndOptionalTime(new ArrayList<>(Arrays.asList(taskRecord.get(3).split(" "))));
            DateAndOptionalTime to = new DateAndOptionalTime(new ArrayList<>(Arrays.asList(taskRecord.get(4).split(" "))));
            task = new Event(name, from, to);
            break;
        default:
            throw new WrongFormatException("Unknown Odin.task type in a Odin.task record.");
        }

        if (isDone.equals("1")) {
            task.markAsDone();
        }

        return task;
    }

    @Override
    public String toString() {
        String res = String.format("[%s][", this.taskType());
        if (this.done) {
            res += "X";
        } else {
            res += " ";
        }
        res += "] " + name;
        return res;
    }
}
