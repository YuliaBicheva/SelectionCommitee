package ua.nure.bycheva.SummaryTask4.web.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.bean.EntrantMarkBean;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.MarkDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Entrant;
import ua.nure.bycheva.SummaryTask4.db.entity.User;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * List entrant's marks command.
 */
public class ListMarksCommand implements Command {
    private static final Logger LOG = Logger.getLogger(ListMarksCommand.class);

    private MarkDAO markDao = (MarkDAO) DAOManager.getInstance().getDAO(Table.MARK);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        HttpSession session = request.getSession();

        Entrant entrant = (Entrant) session.getAttribute("entrant");

        List<EntrantMarkBean> entrantMarks = markDao.findAllEntrantMarks(entrant.getId());

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
        request.setAttribute("testmarks",testmarks);
        request.setAttribute("certmarks",certmarks);



        LOG.debug("Command finished");
        return Path.PAGE_LIST_MARKS_PAGE;
    }
}
