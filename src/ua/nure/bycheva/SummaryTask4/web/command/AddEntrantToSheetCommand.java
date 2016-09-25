package ua.nure.bycheva.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.ApplicationDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.SheetDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Add entrant to the sheet command.
 */
public class AddEntrantToSheetCommand implements Command {

    private static final Logger LOG = Logger.getLogger(AddEntrantToSheetCommand.class);

    private ApplicationDAO applicationDao = (ApplicationDAO) DAOManager.getInstance().getDAO(Table.APPLICATION);

    private SheetDAO sheetDao = (SheetDAO) DAOManager.getInstance().getDAO(Table.SHEET);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        applicationDao.calculateAvgPoints();

        Long sheetId = Long.valueOf(request.getParameter("id"));

        sheetDao.obtainPassedEntrantAndAddToSheet(sheetId);

        LOG.debug("Command finished");
        return Path.SHEET_PAGE + "&id=" + sheetId + "&redirect";
    }
}
