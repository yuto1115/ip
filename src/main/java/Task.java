public class Task {
    final String name;
    boolean done;

    Task(String name) {
        this.name = name;
        this.done = false;
    }

    public void markAsDone() {
        this.done = true;
    }

    public void markAsNotDone() {
        this.done = false;
    }

    @Override
    public String toString() {
        String res = "[";
        if (this.done) res += "X";
        else res += " ";
        res += "] " + name;
        return res;
    }
}
