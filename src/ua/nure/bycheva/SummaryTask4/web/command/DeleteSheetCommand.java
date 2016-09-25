package ua.nure.bycheva.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.SheetDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Delete sheet command.
 */
public class DeleteSheetCommand implements Command {

    private static final Logger LOG = Logger.getLogger(DeleteSheetCommand.class);

    private SheetDAO sheetDao = (SheetDAO) DAOManager.getInstance().getDAO(Table.SHEET);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        sheetDao.delete(Long.valueOf(request.getParameter("id")));

        LOG.debug("Command finished");
        return Path.SHEETS_PAGE+"&redirect";
    }
}
