package minnim.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task with a start and end date.
 * The event has a description and occurs within a specific time range.
 */
public class Events extends Task {
    private LocalDate from;
    private LocalDate to;

    /**
     * Constructs an Events object with a description, start date, and end date.
     *
     * @param description The description of the event.
     * @param from The start date of the event in yyyy-MM-dd format.
     * @param to The end date of the event in yyyy-MM-dd format.
     * @throws IllegalArgumentException If the start date is later than the end date.
     */
    public Events(String description, String from, String to) {
        super(description);
        this.from = parseEvent(from);
        this.to = parseEvent(to);

        if (this.from != null && this.to != null && this.from.isAfter(this.to)) {
            throw new IllegalArgumentException("Event 'from' date cannot be later than 'to' date.");
        }
    }

    /**
     * Parses a date string into a LocalDate object.
     *
     * @param dateStr The date string in yyyy-MM-dd format.
     * @return The parsed LocalDate object, or null if the format is incorrect.
     */
    private LocalDate parseEvent(String dateStr) {
        try {
            // Assuming the format provided is yyyy-MM-dd HHmm (e.g., 2019-12-02 1800)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            // Handle invalid date format here
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return null;
        }
    }

    /**
     * Returns the formatted description of the event.
     *
     * @return A string representation of the event with its description, start, and end dates.
     */
    @Override
    public String getDescription() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[E]" + super.getDescription() + " (from: " + from.format(formatter) + " to: " + to.format(formatter) + ")";
    }

    /**
     * Returns the string representation of the event in a format suitable for file storage.
     *
     * @return A formatted string representing the event for saving to a file.
     */
    @Override
    public String toFileString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("Event | %s | %s | %s | %s", (isDone ? "1" : "0"),
                super.description, from.format(formatter), to.format(formatter));
    }

}
