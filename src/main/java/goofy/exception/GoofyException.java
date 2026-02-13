package goofy.exception;

/**
 * Represents an exception specific to the Goofy application.
 * Thrown when the user provides invalid input or commands.
 */
public class GoofyException extends Exception {

    /**
     * Creates a new GoofyException with the given error message.
     *
     * @param message Error message describing the exception.
     */
    public GoofyException(String message) {
        super(message);
    }
}