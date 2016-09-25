package ua.nure.bycheva.SummaryTask4.web.command;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.ShetStatus;
import ua.nure.bycheva.SummaryTask4.db.dao.ApplicationDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.SheetDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Sheet;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Save sheet command.
 */
public class SaveSheetCommand implements Command {

    private static final Logger LOG = Logger.getLogger(SaveSheetCommand.class);

    private SheetDAO sheetDao = (SheetDAO) DAOManager.getInstance().getDAO(Table.SHEET);
    private ApplicationDAO applicationDao = (ApplicationDAO) DAOManager.getInstance().getDAO(Table.APPLICATION);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        String uid = request.getParameter("uid");
        String facultyId = request.getParameter("facultyId");

        Sheet sheet = new Sheet();
        sheet.setUid(Long.valueOf(uid));
        sheet.setFacultyId(Long.valueOf(facultyId));
        sheet.setStatusId(ShetStatus.IN_WORK.ordinal());
        sheet.setCreateDate(new Date());

        sheetDao.save(sheet);

        applicationDao.calculateAvgPoints();

        sheetDao.obtainPassedEntrantAndAddToSheet(sheet.getId());

        LOG.debug("Command finished");
        return Path.SHEETS_PAGE + "&redirect";
    }
}
