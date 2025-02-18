package odin.task;

import java.util.ArrayList;

import odin.exception.WrongFormatException;

/**
 * Class to manage a list of tasks.
 */
public class TaskList {
    final ArrayList<Task> tasks;
    final ArrayList<TaskListChangeHistory> changeHistory;

    /**
     * Default constructor.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        this.changeHistory = new ArrayList<>();
    }

    /**
     * Returns the number of tasks that are currently registered in the list.
     */
    public int getSize() {
        return this.tasks.size();
    }

    /**
     * Adds a new task to the designated position.
     */
    public void add(int pos, Task task) {
        assert 0 <= pos && pos <= this.tasks.size()
                : "pos should be between 0 and the size ot task list";
        this.tasks.add(pos, task);
        this.changeHistory.add(TaskListChangeHistory.getHistoryForAdd(pos));
    }

    /**
     * Adds a new task to the back of the list.
     */
    public void add(Task task) {
        add(this.tasks.size(), task);
    }

    /**
     * Adds a new task, which is represented by the given task record, to the back of the list.
     *
     * @throws WrongFormatException If the given task record does not follow the correct format.
     */
    public void addFromTaskRecord(ArrayList<String> taskRecord) throws WrongFormatException {
        Task task = Task.restoreFromTaskRecord(taskRecord);
        this.tasks.add(task);
    }

    /**
     * Marks as done the task designated by the given index.
     */
    public void markAsDone(int idx) {
        if (idx < 0 || idx >= this.tasks.size()) {
            throw new IndexOutOfBoundsException();
        }
        this.tasks.get(idx).markAsDone();
        this.changeHistory.add(TaskListChangeHistory.getHistoryForMark(idx));
    }

    /**
     * Marks as not-done the task designated by the given index.
     */
    public void markAsNotDone(int idx) {
        if (idx < 0 || idx >= this.tasks.size()) {
            throw new IndexOutOfBoundsException();
        }
        this.tasks.get(idx).markAsNotDone();
        this.changeHistory.add(TaskListChangeHistory.getHistoryForUnmark(idx));
    }

    /**
     * Deletes the task designated by the given index.
     */
    public void delete(int idx) {
        if (idx < 0 || idx >= this.tasks.size()) {
            throw new IndexOutOfBoundsException();
        }
        Task deletedTask = this.tasks.remove(idx);
        this.changeHistory.add(TaskListChangeHistory.getHistoryForDelete(deletedTask, idx));
    }

    /**
     * Returns the description of the task designated by the given index.
     */
    public String getTaskDescription(int idx) {
        if (idx < 0 || idx >= this.tasks.size()) {
            throw new IndexOutOfBoundsException();
        }
        return this.tasks.get(idx).toString();
    }

    /**
     * Returns the name of the task designated by the given index.
     */
    public String getTaskName(int idx) {
        if (idx < 0 || idx >= this.tasks.size()) {
            throw new IndexOutOfBoundsException();
        }
        return this.tasks.get(idx).name;
    }

    /**
     * Checks if the task designated by the given index is marked as done.
     */
    public boolean checkIfDone(int idx) {
        if (idx < 0 || idx >= this.tasks.size()) {
            throw new IndexOutOfBoundsException();
        }
        return this.tasks.get(idx).checkIfDone();
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

    /**
     * Returns the size of the current change history.
     */
    public int getHistorySize() {
        return this.changeHistory.size();
    }

    /**
     * Reverts the change that is on top of the current change history,
     *  and deletes that change from the history.
     *
     * @return A system message to describe the revert operation.
     */
    public String undo() {
        assert !this.changeHistory.isEmpty() : "change history should not be empty";
        TaskListChangeHistory revertedChange = this.changeHistory.remove(this.changeHistory.size() - 1);
        String message = TaskListChangeHistory.revertChange(this, revertedChange);
        // Need to remove the change history created by this undo operation
        this.changeHistory.remove(this.changeHistory.size() - 1);
        return message;
    }
}
