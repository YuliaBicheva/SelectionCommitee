package ua.nure.bycheva.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.Status;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.EntrantDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.SubjectDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Entrant;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Change entrant status command.
 *
 */
public class ChangeEntrantStatusCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ChangeEntrantStatusCommand.class);

    private SubjectDAO subjectDao = (SubjectDAO) DAOManager.getInstance().getDAO(Table.SUBJECT);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        String[] entratIdArray = request.getParameterValues("entrantId");

        String statusToSet = request.getParameter("statusToSet");

        for(String id: entratIdArray){
            Entrant entrant = ((EntrantDAO) DAOManager.getInstance().getDAO(Table.ENTRANT)).findById(Long.valueOf(id));
            if(statusToSet != null) {
                int statusId = Status.valueOf(statusToSet.toUpperCase()).ordinal();
                entrant.setStatusId(statusId);
            }else {
                entrant.changeStatus();
            }
            ((EntrantDAO) DAOManager.getInstance().getDAO(Table.ENTRANT)).update(entrant);
        }

        LOG.debug("Command finished");
        return Path.LIST_ENTRANTS_PAGE + "&redirect";
    }
}
