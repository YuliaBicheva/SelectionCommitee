package ua.nure.bycheva.SummaryTask4.web.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.bean.EntrantApplicationBean;
import ua.nure.bycheva.SummaryTask4.db.dao.ApplicationDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.User;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * List applications command.
 */
public class ListApplicationsCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ListApplicationsCommand.class);

    private ApplicationDAO applicationDao = (ApplicationDAO) DAOManager.getInstance().getDAO(Table.APPLICATION);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        Long userId = ((User)request.getSession().getAttribute("user")).getId();

        List<EntrantApplicationBean> entrantApplications = applicationDao.findAllByUserId(userId);
        LOG.trace("Found in DB: entrantApplications --> " + entrantApplications.size());

//        String compareParam = request.getParameter("comparator") == null ? DEFAULT_COMPARATOR : request.getParameter("comparator");
//        Comparator<Faculty> comparator = ComparatorManager.get(compareParam);
//        LOG.trace("Obtained comparator --> " + comparator);

//        Collections.sort(faculties,comparator);

        // put menu items list to the request
        request.setAttribute("applications", entrantApplications);
        LOG.trace("Set the request attribute: entrantApplications --> " + entrantApplications);

        LOG.debug("Command finished");
        return Path.PAGE_LIST_APPLICATIONS;
    }
}
