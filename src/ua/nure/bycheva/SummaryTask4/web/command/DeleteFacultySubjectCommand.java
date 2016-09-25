package ua.nure.bycheva.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.FacultySubjectDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Delete faculty subject command.
 */
public class DeleteFacultySubjectCommand implements Command {

    private static final Logger LOG = Logger.getLogger(DeleteFacultySubjectCommand.class);

    private FacultySubjectDAO facultySubjectDao = (FacultySubjectDAO) DAOManager.getInstance().getDAO(Table.FACULTY_SUBJECTS);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        String facultyId = request.getParameter("facultyId");

        String subjectId = request.getParameter("subjectId");

        facultySubjectDao.delete(Long.valueOf(facultyId),Long.valueOf(subjectId));

        LOG.debug("Command finished");
        return Path.EDIT_FACULTY_PAGE + "&id=" + facultyId + "&redirect";
    }
}
