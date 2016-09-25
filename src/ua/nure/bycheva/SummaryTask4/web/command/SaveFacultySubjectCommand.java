package ua.nure.bycheva.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.FacultySubjectDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.FacultySubject;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Save faculty subject command.
 */
public class SaveFacultySubjectCommand implements Command {

    private static final Logger LOG = Logger.getLogger(SaveFacultySubjectCommand.class);

    private static final double DEFAULT_SUBJECT_RATIO = .25;

    private FacultySubjectDAO facultySubjectDao = (FacultySubjectDAO) DAOManager.getInstance().getDAO(Table.FACULTY_SUBJECTS);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        FacultySubject facultySubject = new FacultySubject();

        String facultyId = request.getParameter("id");

        String subjectId = request.getParameter("subjectId");

        facultySubject.setFacultyId(Long.valueOf(facultyId));
        facultySubject.setSubjectId(Long.valueOf(subjectId));
        facultySubject.setRatio(DEFAULT_SUBJECT_RATIO);

        facultySubjectDao.save(facultySubject);

        LOG.debug("Command finished");
        return Path.EDIT_FACULTY_PAGE + "&id=" + facultyId + "&redirect";
    }
}
