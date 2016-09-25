package ua.nure.bycheva.SummaryTask4.web.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.bean.SheetBean;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.SheetDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * List sheets command.
 */
public class ListSheetsCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ListSheetsCommand.class);

    private SheetDAO sheetDao = (SheetDAO) DAOManager.getInstance().getDAO(Table.SHEET);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        List<SheetBean> sheets = sheetDao.findAllSheetBean();

        request.setAttribute("sheets",sheets);

        LOG.debug("Command finished");
        return Path.PAGE_SHEETS_PAGE;
    }
}
