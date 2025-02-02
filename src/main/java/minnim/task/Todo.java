package minnim.task;

public class Todo extends Task {
    protected String date;
    public Todo(String description) {
        super(description);
    }

    @Override
    public String getDescription() {
        return  "[T][" + getStatusIcon() + "] " + this.description;
    }
}
