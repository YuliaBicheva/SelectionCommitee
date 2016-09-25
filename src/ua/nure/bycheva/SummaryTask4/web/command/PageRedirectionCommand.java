package ua.nure.bycheva.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.SubjectDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Created by yulia on 13.08.16.
 */
public class PageRedirectionCommand implements Command {

    private static final Logger LOG = Logger.getLogger(PageRedirectionCommand.class);

    private static final String LINK = "link";

    private SubjectDAO subjectDao = (SubjectDAO) DAOManager.getInstance().getDAO(Table.SUBJECT);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        String obtainedLink = (String)request.getParameter(LINK);
        LOG.trace("Request attribute: obtainedLink --> " + obtainedLink);

        LOG.debug("Command finished");
        return obtainedLink;
    }
}
