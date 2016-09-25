package ua.nure.bycheva.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.SubjectDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Subject;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Edit subject command. Prepare page data for the form.
 */
public class EditSubjectCommand implements Command {

    private static final Logger LOG = Logger.getLogger(EditFacultyCommand.class);

    private SubjectDAO subjectDao = (SubjectDAO) DAOManager.getInstance().getDAO(Table.SUBJECT);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        String subjectId = request.getParameter("id");

        Subject subject = subjectDao.findById(Long.valueOf(subjectId));

        request.setAttribute("subject",subject);

        LOG.debug("Command finished");
        return Path.PAGE_EDIT_SUBJECT_PAGE;
    }
}
