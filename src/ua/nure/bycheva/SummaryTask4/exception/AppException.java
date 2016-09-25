package ua.nure.bycheva.SummaryTask4.exception;

/**
 * An exception that provides information on an application error.
 *
 */
public class AppException extends Exception {

	private static final long serialVersionUID = -5943555237518559665L;

	public AppException() {
        super();
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
