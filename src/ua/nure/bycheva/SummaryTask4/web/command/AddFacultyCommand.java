package ua.nure.bycheva.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Add new faculty.
 */
public class AddFacultyCommand implements Command {

    private static final Logger LOG = Logger.getLogger(AddFacultyCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        LOG.debug("Command finished");
        return Path.PAGE_ADD_FACULTY_PAGE;
    }
}
