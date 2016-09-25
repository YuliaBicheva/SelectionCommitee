package ua.nure.bycheva.SummaryTask4.web.command;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.SubjectDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Subject;
import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.web.sorter.ComparatorManager;

/**
 * List subjects command.
 */
public class ListSubjectsCommand implements Command {
    private static final Logger LOG = Logger.getLogger(ListSubjectsCommand.class);

    private static final String DEFAULT_COMPARATOR = "SNameAZ";

    private SubjectDAO subjectDao = (SubjectDAO) DAOManager.getInstance().getDAO(Table.SUBJECT);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        HttpSession session = request.getSession();

        List<Subject> subjects = subjectDao.findAll();
        LOG.trace("Found in DB: subjects --> " + subjects);

        String compareParam = request.getParameter("comparator") == null ? DEFAULT_COMPARATOR : request.getParameter("comparator");

        session.setAttribute("subjectSort", compareParam);

        Comparator<Subject> comparator = ComparatorManager.get(compareParam);
        LOG.trace("Obtained comparator --> " + comparator);

        Collections.sort(subjects,comparator);

        // put menu items list to the request
        request.setAttribute("subjects", subjects);
        LOG.trace("Set the request attribute: subjects --> " + subjects);

        LOG.debug("Command finished");
        return Path.PAGE_LIST_SUBJECTS_PAGE;
    }
}
