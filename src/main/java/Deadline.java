public class Deadline extends Task {
    protected String date;
    public Deadline(String description, String date) {
        super(description);
        this.date = date;
    }

    @Override
    public String getDescription() {
        return "[D][" + getStatusIcon() + "] " + this.description + " (by:" + this.date + ")" ;
    }

    @Override
    public String toFileString() {
        // Format: Deadline | isDone | description | deadlineDate
        return this.getClass().getSimpleName() + " | " +
                (isDone ? "1" : "0") + " | " + this.description + " | " + date;
    }
}
