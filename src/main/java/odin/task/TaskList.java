package odin.task;

import odin.exception.WrongFormatException;

import java.util.ArrayList;

/**
 * Class to manage a list of tasks.
 */
public class TaskList {
    final ArrayList<Task> tasks;

    /**
     * Default constructor.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Returns the number of tasks that are currently registered in the list.
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Add a new task to the back of the list.
     */
    public void add(Task task) {
        this.tasks.add(task);
    }

    /**
     * Add a new task, which is represented by the given task record, to the back of the list.
     *
     * @throws WrongFormatException If the given task record does not follow the correct format.
     */
    public void addFromTaskRecord(ArrayList<String> taskRecord) throws WrongFormatException {
        Task task = Task.restoreFromTaskRecord(taskRecord);
        this.tasks.add(task);
    }

    /**
     * Mark as done the task designated by the given index.
     */
    public void markAsDone(int idx) {
       if(idx < 0 || idx >= this.tasks.size()) {
           throw new IndexOutOfBoundsException();
       }
       this.tasks.get(idx).markAsDone();
    }

    /**
     * Mark as not-done the task designated by the given index.
     */
    public void markAsNotDone(int idx) {
        if(idx < 0 || idx >= this.tasks.size()) {
            throw new IndexOutOfBoundsException();
        }
        this.tasks.get(idx).markAsNotDone();
    }

    /**
     * Delete the task designated by the given index.
     */
    public void delete(int idx) {
        if(idx < 0 || idx >= this.tasks.size()) {
            throw new IndexOutOfBoundsException();
        }
        this.tasks.remove(idx);
    }

    /**
     * Returns the description of the task designated by the given index.
     */
    public String getTaskDescription(int idx) {
        if(idx < 0 || idx >= this.tasks.size()) {
            throw new IndexOutOfBoundsException();
        }
        return this.tasks.get(idx).toString();
    }

    /**
     * Returns the name of the task designated by the given index.
     */
    public String getTaskName(int idx) {
        if(idx < 0 || idx >= this.tasks.size()) {
            throw new IndexOutOfBoundsException();
        }
        return this.tasks.get(idx).name;
    }

    /**
     * Returns the list of task record of all tasks, each of which is a list of String.
     */
    public ArrayList<ArrayList<String>> getTaskRecordList() {
        ArrayList<ArrayList<String>> taskRecordList = new ArrayList<>();
        for (Task task : this.tasks) {
            taskRecordList.add(task.getTaskRecord());
        }
        return taskRecordList;
    }
}
