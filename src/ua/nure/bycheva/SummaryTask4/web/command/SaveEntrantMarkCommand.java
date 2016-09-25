package ua.nure.bycheva.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.MarkDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Entrant;
import ua.nure.bycheva.SummaryTask4.db.entity.Mark;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Save entrant mark command.
 */
public class SaveEntrantMarkCommand implements Command {

    private static final Logger LOG = Logger.getLogger(SaveEntrantMarkCommand.class);

    private MarkDAO markDao = (MarkDAO) DAOManager.getInstance().getDAO(Table.MARK);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        Entrant entrant = (Entrant) request.getSession().getAttribute("entrant");

        Mark mark = new Mark();

        String subjectId = request.getParameter("subjectId");

        String markValue = request.getParameter("markValue");

        String examType = request.getParameter("examType");

        mark.setEntrantId(entrant.getId());
        mark.setSubjectId(Long.valueOf(subjectId));
        mark.setMarkValue(Double.valueOf(markValue));
        mark.setExamType(examType);
        markDao.save(mark);

        LOG.debug("Command finished");
        return Path.LIST_MARKS_PAGE + "&redirect";
    }

}
