import java.util.ArrayList;

public abstract class Task {
    final String name;
    boolean done;

    public Task(String name) {
        this.name = name;
        this.done = false;
    }

    public void markAsDone() {
        this.done = true;
    }

    public void markAsNotDone() {
        this.done = false;
    }

    public abstract String taskType();


    /**
     * Returns a list of strings to be saved in record.
     */
    public ArrayList<String> getTaskRecord() {
        ArrayList<String> taskRecord = new ArrayList<>();
        taskRecord.add(this.taskType());
        taskRecord.add(this.name);
        taskRecord.add(this.done ? "1" : "0");
        return taskRecord;
    }

    /**
     * Restores a task object from a list of strings saved in a record.
     *
     * @throws Exception If the record does not follow the correct format.
     */
    public static Task restoreFromTaskRecord(ArrayList<String> taskRecord) throws Exception {
        int len = taskRecord.size();
        if (len < 3) {
            throw new Exception();
        }

        String type = taskRecord.get(0);
        String name = taskRecord.get(1);
        String isDone = taskRecord.get(2);
        Task task;

        switch (type) {
        case "T":
            if (len != 3) {
                throw new Exception();
            }
            task = new Todo(name);
            break;
        case "D":
            if (len != 4) {
                throw new Exception();
            }
            String by = taskRecord.get(3);
            task = new Deadline(name, by);
            break;
        case "E":
            if (len != 5) {
                throw new Exception();
            }
            String from = taskRecord.get(3);
            String to = taskRecord.get(4);
            task = new Event(name, from, to);
            break;
        default:
            throw new Exception();
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
