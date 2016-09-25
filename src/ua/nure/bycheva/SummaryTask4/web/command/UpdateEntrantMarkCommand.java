package ua.nure.bycheva.SummaryTask4.web.command;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.MarkDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Mark;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Update entrant marks command.
 */
public class UpdateEntrantMarkCommand implements Command {

    private static final Logger LOG = Logger.getLogger(UpdateEntrantMarkCommand.class);

    private MarkDAO markDao = (MarkDAO) DAOManager.getInstance().getDAO(Table.MARK);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        Map<String, Mark> entrantMarksForUpdateMap = new TreeMap<>();

        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String paramName : parameterMap.keySet()) {
            if (paramName.startsWith("mark")) {
                String[] paramNamePieces = paramName.split("_");
                String markId = paramNamePieces[1];
                String contentValue = paramNamePieces[2];

                String paramValue = parameterMap.get(paramName)[0];

                Mark entrantMark;

                if (entrantMarksForUpdateMap.containsKey(markId)) {
                    entrantMark = entrantMarksForUpdateMap.get(markId);

                } else {
                    entrantMark = new Mark();
                    entrantMark.setId(Long.valueOf(markId));
                }

                if (contentValue.equals("test") || contentValue.equals("certificate")) {
                    entrantMark.setMarkValue(Double.valueOf(paramValue));
                }

                entrantMarksForUpdateMap.put(markId, entrantMark);
            }
        }

        LOG.trace("Obtain faculty subject map: entrantMarksForUpdateMap --> " + entrantMarksForUpdateMap);

        markDao.update(new ArrayList(entrantMarksForUpdateMap.values()));

        LOG.debug("Command finished");
        return Path.LIST_MARKS_PAGE + "&redirect";
    }
}
