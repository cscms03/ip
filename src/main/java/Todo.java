public class Todo extends Task {
    protected String date;
    public Todo(String description, String date) {
        super(description);
        this.date = date;
    }

    @Override
    public String getDescription() {
        return " ";
    }
}
