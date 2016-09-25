package ua.nure.bycheva.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.SubjectDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;

/**
 * Empty command. Redirect to the main page.
 */
public class NoCommand implements Command {

    private static final Logger LOG = Logger.getLogger(NoCommand.class);

    private SubjectDAO subjectDao = (SubjectDAO) DAOManager.getInstance().getDAO(Table.SUBJECT);


    @Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Command starts");

        LOG.debug("Command finished");
        return Path.PAGE_MAIN_PAGE;
	}

}
