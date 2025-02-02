package minnim.task;

public class Task {
    protected String description;
    protected boolean isDone;


    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void setMarked() {
        this.isDone = true;
    }

    public void setUnmarked() {
        this.isDone = false;
    }

    public String getDescription() {
        return  "[" + getStatusIcon() + "] " + this.description;
    }

    public String toFileString() {
        return this.getClass().getSimpleName() + " | " +
                (isDone ? "1" : "0") + " | " + description;
    }
}
