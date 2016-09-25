package ua.nure.bycheva.SummaryTask4.web.command;

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
 * Save faculty command.
 */
public class SaveFacultyCommand implements Command {

    private static final Logger LOG = Logger.getLogger(SaveFacultyCommand.class);

    private FacultyDAO facultyDao = (FacultyDAO) DAOManager.getInstance().getDAO(Table.FACULTY);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        String name = request.getParameter("name");
        LOG.trace("Request parameter: name --> " + name);

        String budgetPlaces = request.getParameter("budgetPlaces");
        LOG.trace("Request parameter: budgetPlaces --> " + budgetPlaces);

        String totalPlaces = request.getParameter("totalPlaces");
        LOG.trace("Request parameter: totalPlaces --> " + totalPlaces);

        Faculty faculty = new Faculty();

        faculty.setName(name);
        faculty.setBudgetPlaces(Integer.valueOf(budgetPlaces));
        faculty.setTotalPlaces(Integer.valueOf(totalPlaces));

        facultyDao.save(faculty);


        LOG.debug("Command finished");
        return Path.LIST_FACULTIES_COMMAND + "&redirect";
    }
}
