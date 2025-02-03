package minnim.task;

/**
 * Represents a task with a description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task with the given description.
     * By default, the task is not marked as done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task.
     * "X" indicates the task is done, and " " (space) indicates it is not done.
     *
     * @return A string representing the status of the task.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks the task as completed.
     */
    public void setMarked() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void setUnmarked() {
        this.isDone = false;
    }

    /**
     * Returns the formatted description of the task, including its status icon.
     *
     * @return A string representation of the task with its completion status.
     */
    public String getDescription() {
        return  "[" + getStatusIcon() + "] " + this.description;
    }

    /**
     * Returns the string representation of the task formatted for file storage.
     *
     * @return A formatted string representing the task for saving to a file.
     */
    public String toFileString() {
        return this.getClass().getSimpleName() + " | " +
                (isDone ? "1" : "0") + " | " + description;
    }
}
