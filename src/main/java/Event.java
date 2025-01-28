public class Event extends Task {
    private final String from, to;

    public Event(String str, String from, String to) {
        super(str);
        this.from = from;
        this.to = to;
    }

    @Override
    public String taskType() {
        return "E";
    }

    @Override
    public String toString() {
        return String.format("%s (from: %s to: %s)", super.toString(), this.from, this.to);
    }
}
