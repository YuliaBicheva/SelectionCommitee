package ua.nure.bycheva.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.ApplicationDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Delete application command.
 */
public class DeleteApplicationCommand implements Command {

    private static final Logger LOG = Logger.getLogger(DeleteApplicationCommand.class);

    private ApplicationDAO applicationDao = (ApplicationDAO) DAOManager.getInstance().getDAO(Table.APPLICATION);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        applicationDao.delete(Long.valueOf(request.getParameter("id")));

        LOG.debug("Command finished");
        return Path.LIST_APPLICATIONS_PAGE +"&redirect";
    }
}
