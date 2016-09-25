package ua.nure.bycheva.SummaryTask4.web.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.FacultyDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Faculty;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Add sheet command.
 */
public class AddSheetCommand implements Command {

    private static final Logger LOG = Logger.getLogger(AddSheetCommand.class);

    private FacultyDAO facultyDao = (FacultyDAO) DAOManager.getInstance().getDAO(Table.FACULTY);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        List<Faculty> faculties = facultyDao.findAllNotExistsInSheets();

        request.setAttribute("faculties",faculties);

        LOG.debug("Command finished");
        return Path.PAGE_ADD_SHEET_PAGE;
    }
}
