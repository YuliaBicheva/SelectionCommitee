package ua.nure.bycheva.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.bean.SheetBean;
import ua.nure.bycheva.SummaryTask4.db.bean.SheetEntrantBean;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.SheetDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 Sheet view command.
 */
public class ShowSheetCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ShowSheetCommand.class);

    private SheetDAO sheetDao = (SheetDAO) DAOManager.getInstance().getDAO(Table.SHEET);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException, IOException {
        LOG.debug("Command starts");

        Long sheetId = Long.valueOf(request.getParameter("id"));

        SheetBean sheet = (SheetBean) sheetDao.findSheetBeanById(sheetId);


        List<SheetEntrantBean> entrants = sheetDao.findAllEntrantsSheets(sheetId);

        request.setAttribute("sheet", sheet);

        request.setAttribute("entrants", entrants);

        LOG.debug("Command finished");
        return Path.PAGE_SHEET_PAGE;
    }
}
