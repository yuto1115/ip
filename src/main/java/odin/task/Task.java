package odin.task;

import java.util.ArrayList;
import java.util.Arrays;

import odin.exception.WrongFormatException;
import odin.parser.DateAndOptionalTime;

/**
 * Abstract class to represent a task.
 */
public abstract class Task {
    final String name;
    private boolean isDone;

    /**
     * Default constructor.
     *
     * @param name Description of the task.
     */
    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     */
    void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not-done.
     */
    void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns a single character that represents the type of the task.
     */
    abstract String taskType();

    /**
     * Returns task record of the task.
     */
    ArrayList<String> getTaskRecord() {
        ArrayList<String> taskRecord = new ArrayList<>();
        taskRecord.add(this.taskType());
        taskRecord.add(this.name);
        taskRecord.add(this.isDone ? "1" : "0");
        return taskRecord;
    }

    /**
     * Restores a task object from given task record.
     *
     * @throws WrongFormatException If the task record does not follow the correct format.
     */
    static Task restoreFromTaskRecord(ArrayList<String> taskRecord) throws WrongFormatException {
        int len = taskRecord.size();
        if (len < 3) {
            throw new WrongFormatException("Number of tokens in a task record must be at least three.");
        }

        String type = taskRecord.get(0);
        String name = taskRecord.get(1);
        String isDone = taskRecord.get(2);
        Task task;

        switch (type) {
        case "T":
            if (len != 3) {
                throw new WrongFormatException("Number of tokens in a task record of todo must be three.");
            }
            task = new Todo(name);
            break;
        case "D":
            if (len != 4) {
                throw new WrongFormatException("Number of tokens in a task record of deadline must be four.");
            }
            DateAndOptionalTime by = new DateAndOptionalTime(
                    new ArrayList<>(Arrays.asList(taskRecord.get(3).split(" "))));
            task = new Deadline(name, by);
            break;
        case "E":
            if (len != 5) {
                throw new WrongFormatException("Number of tokens in a task record of event must be five.");
            }
            DateAndOptionalTime from = new DateAndOptionalTime(
                    new ArrayList<>(Arrays.asList(taskRecord.get(3).split(" "))));
            DateAndOptionalTime to = new DateAndOptionalTime(
                    new ArrayList<>(Arrays.asList(taskRecord.get(4).split(" "))));
            task = new Event(name, from, to);
            break;
        default:
            throw new WrongFormatException("Unknown task type in a task record.");
        }

        if (isDone.equals("1")) {
            task.markAsDone();
        }

        return task;
    }

    @Override
    public String toString() {
        String res = String.format("[%s][", this.taskType());
        if (this.isDone) {
            res += "X";
        } else {
            res += " ";
        }
        res += "] " + name;
        return res;
    }
}
