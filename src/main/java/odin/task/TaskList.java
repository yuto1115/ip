package odin.task;

import odin.exception.WrongFormatException;

import java.util.ArrayList;

public class TaskList {
    ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public int getSize() {
        return tasks.size();
    }

    public void add(Task task) {
        this.tasks.add(task);
    }

    public void addFromTaskRecord(ArrayList<String> taskRecord) throws WrongFormatException {
        Task task = Task.restoreFromTaskRecord(taskRecord);
        this.tasks.add(task);
    }

    public void markAsDone(int idx) {
       if(idx < 0 || idx >= this.tasks.size()) {
           throw new IndexOutOfBoundsException();
       }
       this.tasks.get(idx).markAsDone();
    }

    public void markAsNotDone(int idx) {
        if(idx < 0 || idx >= this.tasks.size()) {
            throw new IndexOutOfBoundsException();
        }
        this.tasks.get(idx).markAsNotDone();
    }

    public void delete(int idx) {
        if(idx < 0 || idx >= this.tasks.size()) {
            throw new IndexOutOfBoundsException();
        }
        this.tasks.remove(idx);
    }

    public String getTaskDescription(int idx) {
        if(idx < 0 || idx >= this.tasks.size()) {
            throw new IndexOutOfBoundsException();
        }
        return this.tasks.get(idx).toString();
    }

    public ArrayList<ArrayList<String>> getTaskRecordList() {
        ArrayList<ArrayList<String>> taskRecordList = new ArrayList<>();
        for (Task task : this.tasks) {
            taskRecordList.add(task.getTaskRecord());
        }
        return taskRecordList;
    }
}
