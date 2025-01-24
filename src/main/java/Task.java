public class Task {
    final String name;
    Boolean done;

    Task(String name) {
        this.name = name;
        this.done = false;
    }

    public void getDone() {
        this.done = true;
    }

    public void getNotDone() {
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
