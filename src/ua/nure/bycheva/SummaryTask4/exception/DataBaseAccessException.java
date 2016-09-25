package ua.nure.bycheva.SummaryTask4.exception;

/**
 * An exception that provides information on a database access error.
 *
 */
public class DataBaseAccessException extends AppException {

	private static final long serialVersionUID = -6177781188758872266L;

	public DataBaseAccessException() {
    }

    public DataBaseAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
