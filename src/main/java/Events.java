import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Events extends Task{
    private LocalDate from;
    private LocalDate to;

    public Events(String description, String from, String to) {
        super(description);
        this.from = parseEvent(from);
        this.to = parseEvent(to);

        if (this.from != null && this.to != null && this.from.isAfter(this.to)) {
            throw new IllegalArgumentException("Event 'from' date cannot be later than 'to' date.");
        }
    }

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

    @Override
    public String getDescription() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[E]" + super.getDescription() + " (from: " + from.format(formatter) + " to: " + to.format(formatter) + ")";
    }

    @Override
    public String toFileString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("EVENT | %s | %s | %s | %s", (isDone ? "1" : "0"),
                super.description, from.format(formatter), to.format(formatter));
    }

}
