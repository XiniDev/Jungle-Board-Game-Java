package jungle;

/**
 * The IllegalMoveException class is an exception on an illegal move.
 * This extends from the RuntimeException so it is only handled when checked.
 */
public class IllegalMoveException extends RuntimeException {

    /**
     * Constructs a new IllegalMoveException with a default message.
     */
    public IllegalMoveException() {
        super("Illegal move attempted.");
    }

    /**
     * Constructs a new IllegalMoveException with a specified message.
     * 
     * @param message the specified message for the exception.
     */
    public IllegalMoveException(String message) {
        super(message);
    }

    /**
     * Constructs a new IllegalMoveException with a specified message and cause.
     * 
     * @param message the specified message for the exception.
     * @param cause the specified cause for the exception.
     */
    public IllegalMoveException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new IllegalMoveException with a specified cause.
     * 
     * @param cause the specified cause for the exception.
     */
    public IllegalMoveException(Throwable cause) {
        super(cause);
    }
}
