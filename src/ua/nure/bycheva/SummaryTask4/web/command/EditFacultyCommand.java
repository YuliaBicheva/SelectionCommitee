package ua.nure.bycheva.SummaryTask4.web.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.bean.FacultySubjectBean;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.FacultyDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.FacultySubjectDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Faculty;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Edit faculty command. Prepare page data for the form.
 */
public class EditFacultyCommand implements Command {

    private static final Logger LOG = Logger.getLogger(EditFacultyCommand.class);

    private FacultySubjectDAO facultySubjectDao = (FacultySubjectDAO) DAOManager.getInstance().getDAO(Table.FACULTY_SUBJECTS);
    private FacultyDAO facultyDao = (FacultyDAO) DAOManager.getInstance().getDAO(Table.FACULTY);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        String facultyId = request.getParameter("id");

        Faculty faculty = facultyDao.findById(Long.valueOf(facultyId));

        List<FacultySubjectBean> facultySubjectBeanList = facultySubjectDao.findAllFacultySubjectsBeans(Long.valueOf(facultyId));

        request.setAttribute("faculty",faculty);

        request.setAttribute("facultySubjects", facultySubjectBeanList);

        LOG.debug("Command finished");
        return Path.PAGE_EDIT_FACULTY_PAGE;
    }
}
