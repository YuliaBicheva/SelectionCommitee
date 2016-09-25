package ua.nure.bycheva.SummaryTask4.web.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.bean.ApplicationMarkBean;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.MarkDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.SubjectDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Entrant;
import ua.nure.bycheva.SummaryTask4.db.entity.Subject;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Create application command.
 */
public class CreateApplicationCommand implements Command {

    private static final Logger LOG = Logger.getLogger(CreateApplicationCommand.class);

    private MarkDAO markDao = (MarkDAO) DAOManager.getInstance().getDAO(Table.MARK);
    private SubjectDAO subjectDao = (SubjectDAO) DAOManager.getInstance().getDAO(Table.SUBJECT);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        HttpSession session = request.getSession();

        Entrant entrant = (Entrant) session.getAttribute("entrant");

        String facultyId = request.getParameter("id");

        List<Subject> testFacultySubjects = subjectDao.findAllTestSubjectsWhichEntrantHaveNot(Long.valueOf(facultyId), entrant.getId());

        LOG.trace("Obtain subjects for test by faculty id:  testFacultySubjects --> " + testFacultySubjects);

        List<Subject> certificateSubjects = subjectDao.findAllCertificateSubjectsWhichEntrantNotHave(entrant.getId());
        LOG.trace("Obtain subjects for certificates:  certificateSubjects --> " + certificateSubjects);

        List<ApplicationMarkBean> testmarks = markDao.findAllEntrantMarksForApplication(entrant.getUserId(), Long.valueOf(facultyId));
        List<ApplicationMarkBean> certmarks = markDao.findAllCertificateMarksForApplication(entrant.getUserId());

        request.setAttribute("id",facultyId);
        request.setAttribute("facultySubjects",testFacultySubjects);
        request.setAttribute("subjects", certificateSubjects);
        request.setAttribute("testmarks", testmarks);
        request.setAttribute("certmarks", certmarks);

        LOG.debug("Command finished");
        return Path.PAGE_APPLY_FACULTY_PAGE;
    }

}
