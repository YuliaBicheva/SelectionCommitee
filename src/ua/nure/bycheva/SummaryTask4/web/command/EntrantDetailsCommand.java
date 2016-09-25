package ua.nure.bycheva.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.bean.EntrantApplicationBean;
import ua.nure.bycheva.SummaryTask4.db.bean.EntrantMarkBean;
import ua.nure.bycheva.SummaryTask4.db.bean.UserEntrantBean;
import ua.nure.bycheva.SummaryTask4.db.dao.*;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yulia on 01.01.01.
 */
public class EntrantDetailsCommand implements Command {

    private static final Logger LOG = Logger.getLogger(EntrantDetailsCommand.class);

    private ApplicationDAO applicationDAO = (ApplicationDAO) DAOManager.getInstance().getDAO(Table.APPLICATION);
    private MarkDAO markDAO = (MarkDAO) DAOManager.getInstance().getDAO(Table.MARK);
    private EntrantDAO entrantDAO = (EntrantDAO) DAOManager.getInstance().getDAO(Table.ENTRANT);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException, IOException {
        LOG.debug("Command starts");

        String entrantId = request.getParameter("entrantId");

        Long eId = Long.valueOf(entrantId);

        UserEntrantBean entrant = entrantDAO.findUserEntrantBean(eId);

        List<EntrantApplicationBean> entrantApplications = applicationDAO.findAllByEntrantId(eId);

        List<EntrantMarkBean> entrantMarks = markDAO.findAllEntrantMarks(eId);

        List<EntrantMarkBean> testmarks = new ArrayList<>();
        List<EntrantMarkBean> certmarks = new ArrayList<>();

        for(EntrantMarkBean em: entrantMarks){
            if(em.getExamType().equals("test")){
                testmarks.add(em);
            }
            if(em.getExamType().equals("certificate")){
                certmarks.add(em);
            }
        }

        request.setAttribute("entrant",entrant);
        request.setAttribute("applications",entrantApplications);
        request.setAttribute("testmarks",testmarks);
        request.setAttribute("certmarks",certmarks);

        LOG.debug("Command finished");
        return Path.PAGE_ENTRANT_DETAILS_PAGE;
    }
}
