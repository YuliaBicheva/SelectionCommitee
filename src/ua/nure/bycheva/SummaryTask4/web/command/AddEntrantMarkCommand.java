package ua.nure.bycheva.SummaryTask4.web.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.SubjectDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Entrant;
import ua.nure.bycheva.SummaryTask4.db.entity.Subject;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Add entrant mark command.
 */
public class AddEntrantMarkCommand implements Command {

    private static final Logger LOG = Logger.getLogger(AddEntrantMarkCommand.class);

    private SubjectDAO subjectDao = (SubjectDAO) DAOManager.getInstance().getDAO(Table.SUBJECT);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        HttpSession session = request.getSession();

        Entrant entrant = (Entrant) session.getAttribute("entrant");

        String examType = request.getParameter("examType");

        request.setAttribute("examType", examType);

        List<Subject> subjects = subjectDao.findAllNotExistInMarks(entrant.getId(), examType);

        request.setAttribute("subjects",subjects);


        LOG.debug("Command finished");
        return Path.PAGE_ADD_ENTRANT_MARK_PAGE;
    }
}
