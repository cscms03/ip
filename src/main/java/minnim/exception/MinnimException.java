package minnim.exception;

/**
 * Represents a base exception for the Minnim application.
 * This is an abstract class that extends Exception
 * and serves as a parent for specific custom exceptions.
 */
public abstract class MinnimException extends Exception{

    /**
     * Returns a string representation of the exception.
     * This method is overridden to provide a custom message.
     *
     * @return A sad face ":(" representing an error state.
     */
    @Override
    public String toString() {
        return ":(";
    }
}
