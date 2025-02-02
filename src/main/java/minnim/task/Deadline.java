package minnim.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private String description;
    private LocalDate deadline;

    public Deadline(String description, String deadlineStr) {
        super(description);
        this.deadline = parseDeadline(deadlineStr);
    }

    private LocalDate parseDeadline(String deadlineStr) {
        try {
            // Assuming the format provided is yyyy-MM-dd HHmm (e.g., 2019-12-02 1800)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(deadlineStr, formatter);
        } catch (DateTimeParseException e) {
            // Handle invalid date format here
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return null;
        }
    }

    @Override
    public String toFileString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("minnim.task.Deadline | %s | %s | %s", (isDone ? "1" : "0"),
                super.description, deadline.format(formatter));
    }

    @Override
    public String getDescription() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[D]" + super.getDescription() + " (by: " + deadline.format(formatter) + ")";
    }
}