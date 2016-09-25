package ua.nure.bycheva.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.FacultyDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Delete faculty command.
 */
public class DeleteFacultyCommand implements Command {

    private static final Logger LOG = Logger.getLogger(DeleteFacultyCommand.class);

    private FacultyDAO facultyDao = (FacultyDAO) DAOManager.getInstance().getDAO(Table.FACULTY);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        String id = request.getParameter("id");

        facultyDao.delete(Long.valueOf(id));

        LOG.debug("Command finished");
        return Path.LIST_FACULTIES_COMMAND + "&redirect";
    }
}
