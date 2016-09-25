package ua.nure.bycheva.SummaryTask4.web.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.bean.UserEntrantBean;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.EntrantDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.SubjectDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * List entrants command.
 */
public class ListEntrantsCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ListEntrantsCommand.class);

    private SubjectDAO subjectDao = (SubjectDAO) DAOManager.getInstance().getDAO(Table.SUBJECT);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        List<UserEntrantBean> entrantBeans = ((EntrantDAO) DAOManager.getInstance().getDAO(Table.ENTRANT)).findAllUserEntrantBeans();

        request.setAttribute("entrants", entrantBeans);

        LOG.debug("Command finished");
        return Path.PAGE_LIST_ENTRANTS_PAGE;
    }
}
