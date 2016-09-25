package ua.nure.bycheva.SummaryTask4.web.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.ApplicationDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.MarkDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Application;
import ua.nure.bycheva.SummaryTask4.db.entity.Mark;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Apply into faculty by entrant.
 */
public class ApplyFacultyCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ApplyFacultyCommand.class);
    private ApplicationDAO applicationDao = (ApplicationDAO) DAOManager.getInstance().getDAO(Table.APPLICATION);

    private MarkDAO markDao = (MarkDAO) DAOManager.getInstance().getDAO(Table.MARK);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        String facultyId = request.getParameter("id");

        String entrantId = request.getParameter("entrantId");

        Application application = new Application();

        application.setEntrantId(Long.valueOf(entrantId));
        application.setFacultyId(Long.valueOf(facultyId));
        application.setCreateDate(new Date());

        applicationDao.save(application);

        LOG.info("Create application: application " + application);

        List<Mark> entrantMarks = new ArrayList<>();

        Map<String, String[]> parameterMap = request.getParameterMap();

        for (String paramName: parameterMap.keySet()) {
            if (paramName.startsWith("subject_")) {
                String[] paramNamePieces = paramName.split("_");
                String subjectId = paramNamePieces[1];
                String examType = paramNamePieces[2];

                String paramValue = parameterMap.get(paramName)[0];

                Mark mark = new Mark();
                mark.setEntrantId(Long.valueOf(entrantId));
                mark.setSubjectId(Long.valueOf(subjectId));
                mark.setMarkValue(Double.valueOf(paramValue));
                mark.setExamType(examType);

                entrantMarks.add(mark);
            }
        }

        markDao.save(entrantMarks);

        LOG.info("Create entrant marks: allMarks" + entrantMarks);

        LOG.debug("Command finished");
        return Path.LIST_FACULTIES_COMMAND + "&redirect";
    }
}
