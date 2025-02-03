package minnim.exception;

public class MinnimMissingTaskDetailException extends MinnimException{

    /**
     * Returns a string representation of the exception.
     * Provides a user-friendly error message indicating a missing task detail.
     *
     * @return A formatted error message specifying the missing task detail requirement.
     */
    @Override
    public String toString() {
        return String.format("You are missing your task name. Please Provide one! %s", super.toString());
    }
 }
