package minnim.task;

/**
 * Represents a Todo task, which is a type of Task that does not have a specific date or time.
 */
public class Todo extends Task {
    protected String date;

    /**
     * Constructs a Todo task with the specified description.
     *
     * @param description The description of the Todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the Todo task with its type and status icon.
     *
     * @return A formatted string representing the Todo task.
     */
    @Override
    public String getDescription() {
        return  "[T][" + getStatusIcon() + "] " + this.description;
    }
}
