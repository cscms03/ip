public class Events extends Task{
    protected String from;
    protected String to;

    public Events(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getDescription() {
        return "[E][" + getStatusIcon() + "] " + this.description + " (from:" + this.from + " to:" + this.to + ")";
    }

    @Override
    public String toFileString() {
        return this.getClass().getSimpleName() + " | " +
                (isDone ? "1" : "0") + " | " + this.description + " | " + from + " | " + to;
    }
}
