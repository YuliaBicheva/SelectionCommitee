package ua.nure.bycheva.SummaryTask4.web.command;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.FacultyDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.FacultySubjectDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Faculty;
import ua.nure.bycheva.SummaryTask4.db.entity.FacultySubject;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Update faculty command.
 */
public class UpdateFacultyCommand implements Command {

    private static final Logger LOG = Logger.getLogger(UpdateFacultyCommand.class);

    private FacultyDAO facultyDao = (FacultyDAO) DAOManager.getInstance().getDAO(Table.FACULTY);
    private FacultySubjectDAO facultySubjectDAO = (FacultySubjectDAO) DAOManager.getInstance().getDAO(Table.FACULTY_SUBJECTS);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        String id = request.getParameter("id");
        LOG.trace("Request parameter: id --> " + id);

        String name = request.getParameter("name");
        LOG.trace("Request parameter: name --> " + name);

        String budgetPlaces = request.getParameter("budgetPlaces");
        LOG.trace("Request parameter: budgetPlaces --> " + budgetPlaces);

        String totalPlaces = request.getParameter("totalPlaces");
        LOG.trace("Request parameter: totalPlaces --> " + totalPlaces);

        Faculty faculty = new Faculty();

        faculty.setId(Long.valueOf(id));
        faculty.setName(name);
        faculty.setBudgetPlaces(Integer.valueOf(budgetPlaces));
        faculty.setTotalPlaces(Integer.valueOf(totalPlaces));

        facultyDao.update(faculty);

        LOG.trace("Update faculty: faculty --> " + faculty);

        Map<String, FacultySubject> facultySubjectMap = new TreeMap<>();

        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String paramName : parameterMap.keySet()) {
            if (paramName.startsWith("subject_")) {
                String[] paramNamePieces = paramName.split("_");
                String subjectId = paramNamePieces[1];
                String contentValue = paramNamePieces[2];

                String paramValue = parameterMap.get(paramName)[0];

                FacultySubject facultySubject;

                if (facultySubjectMap.containsKey(subjectId)) {
                    facultySubject = facultySubjectMap.get(subjectId);

                } else {
                    facultySubject = new FacultySubject();
                    facultySubject.setFacultyId(faculty.getId());
                    facultySubject.setSubjectId(Long.valueOf(subjectId));
                }

                if (contentValue.equals("ratio")) {
                    facultySubject.setRatio(Double.valueOf(paramValue));
                }

                facultySubjectMap.put(subjectId, facultySubject);
            }
        }

        LOG.trace("Obtain faculty subject map: facultySubjectsMap --> " + facultySubjectMap);

        facultySubjectDAO.update(new ArrayList(facultySubjectMap.values()));

        LOG.debug("Command finished");
        return Path.EDIT_FACULTY_PAGE + "&id=" + faculty.getId() + "&redirect";
    }
}
