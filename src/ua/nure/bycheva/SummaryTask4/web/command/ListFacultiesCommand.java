package ua.nure.bycheva.SummaryTask4.web.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.Role;
import ua.nure.bycheva.SummaryTask4.db.bean.EntrantApplicationBean;
import ua.nure.bycheva.SummaryTask4.db.dao.ApplicationDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.FacultyDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Entrant;
import ua.nure.bycheva.SummaryTask4.db.entity.Faculty;
import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.web.sorter.ComparatorManager;

/**
 * List faculties command.
 */
public class ListFacultiesCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ListFacultiesCommand.class);

    private static final String DEFAULT_COMPARATOR = "FNameAZ";
    
    private static final int PAGING_EVERY = 5;

    private FacultyDAO facultyDao = (FacultyDAO) DAOManager.getInstance().getDAO(Table.FACULTY);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        HttpSession session = request.getSession();

        List<Faculty> faculties = facultyDao.findAll();
        LOG.trace("Found in DB: facultyList --> " + faculties.size());
        

        String compareParam = request.getParameter("comparator") == null ? DEFAULT_COMPARATOR : request.getParameter("comparator");

        session.setAttribute("facultySort", compareParam);

        Comparator<Faculty> comparator = ComparatorManager.get(compareParam);
        LOG.trace("Obtained comparator --> " + comparator);

        Collections.sort(faculties,comparator);

        if(request.getSession().getAttribute("userRole") == Role.ENTRANT){
            List<Long> appliedFaculties = new ArrayList<>();
            Entrant entrant = (Entrant) request.getSession().getAttribute("entrant");
            List<EntrantApplicationBean> entrantApp = ((ApplicationDAO)DAOManager.getInstance().getDAO(Table.APPLICATION)).findAllByUserId(entrant.getUserId());
            for(EntrantApplicationBean app: entrantApp){
                appliedFaculties.add(app.getFacultyId());
            }
            request.setAttribute("appliedFaculties", appliedFaculties);
        }

        // put menu items list to the request
        request.setAttribute("faculties", faculties);
        LOG.trace("Set the request attribute: faculties --> " + faculties);

        LOG.debug("Command finished");
        return Path.PAGE_LIST_FACULTIES;
    }
}
