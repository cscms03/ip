package minnim.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a deadline task with a specific due date.
 */
public class Deadline extends Task {
    private String description;
    private LocalDate deadline;

    /**
     * Constructs a Deadline task with a description and a deadline date.
     *
     * @param description The description of the task.
     * @param deadlineStr The deadline date in the format yyyy-MM-dd.
     */
    public Deadline(String description, String deadlineStr) {
        super(description);
        this.deadline = parseDeadline(deadlineStr);
    }

    /**
     * Parses the deadline string into a LocalDate object.
     *
     * @param deadlineStr The deadline date as a string in yyyy-MM-dd format.
     * @return A LocalDate object representing the deadline, or null if the format is invalid.
     */
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

    /**
     * Converts the deadline task into a file-friendly string format.
     *
     * @return A string representation of the task formatted for file storage.
     */
    @Override
    public String toFileString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("Deadline | %s | %s | %s", (isDone ? "1" : "0"),
                super.description, deadline.format(formatter));
    }

    /**
     * Returns the formatted description of the deadline task.
     *
     * @return A string representation of the task with its deadline.
     */
    @Override
    public String getDescription() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[D]" + super.getDescription() + " (by: " + deadline.format(formatter) + ")";
    }
}