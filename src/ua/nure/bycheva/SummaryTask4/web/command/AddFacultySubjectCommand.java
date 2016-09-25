package ua.nure.bycheva.SummaryTask4.web.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.FacultyDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.SubjectDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Faculty;
import ua.nure.bycheva.SummaryTask4.db.entity.Subject;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Add new faculty subject command.
 */
public class AddFacultySubjectCommand implements Command {

    private static final Logger LOG = Logger.getLogger(AddFacultySubjectCommand.class);

    private SubjectDAO subjectDao = (SubjectDAO) DAOManager.getInstance().getDAO(Table.SUBJECT);
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        String facultyId = request.getParameter("id");

        Faculty faculty = ((FacultyDAO)DAOManager.getInstance().getDAO(Table.FACULTY)).findById(Long.valueOf(facultyId));

        request.setAttribute("facultyId", facultyId);

        List<Subject> subjects = subjectDao.findAllNotExistInFaculty(Long.valueOf(facultyId));

        request.setAttribute("facultyName", faculty.getName());
        request.setAttribute("subjects", subjects);

        LOG.debug("Command finished");
        return Path.PAGE_ADD_FACULTY_SUBJECT_PAGE;
    }
}
