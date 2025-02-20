package minnim.exception;

/**
 * Exception thrown when a deadline or event task is missing a required date.
 * This exception extends {@link MinnimException}.
 */
public class MinnimMissingDateException extends MinnimException{

    /**
     * Returns a string representation of the exception.
     * Provides a user-friendly error message indicating a missing date.
     *
     * @return A formatted error message specifying the missing date requirement.
     */
    @Override
    public String toString() {
        return String.format("You are missing date for deadline/event task, or is in incorrect format."
                        + "Please provide as necessary. %s", super.toString());
    }
}
