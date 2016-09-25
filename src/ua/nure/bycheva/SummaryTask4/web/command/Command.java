package ua.nure.bycheva.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.bycheva.SummaryTask4.exception.AppException;


/**
 * Main interface for the Command pattern implementation.
 *
 *
 */
public interface Command {

    /**
     * Execution method for command.
     *
     * @return Address to go once the command is executed.
     */
	String execute(HttpServletRequest request, HttpServletResponse response) throws AppException, IOException;

}
