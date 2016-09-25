package ua.nure.bycheva.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.MarkDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Delete entrant mark.
 */
public class DeleteEntrantMarkCommand implements Command {

    private static final Logger LOG = Logger.getLogger(DeleteEntrantMarkCommand.class);
    private MarkDAO markDao = (MarkDAO) DAOManager.getInstance().getDAO(Table.MARK);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        String markId = request.getParameter("id");

        markDao.delete(Long.valueOf(markId));

        LOG.debug("Command finished");
        return Path.LIST_MARKS_PAGE + "&redirect";
    }
}
