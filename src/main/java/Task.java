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

    @Override
    public String toString() {
        String res = String.format("[%s][", this.taskType());
        if (this.done) res += "X";
        else res += " ";
        res += "] " + name;
        return res;
    }
}
