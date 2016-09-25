package ua.nure.bycheva.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.SubjectDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Subject;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Update subject command.
 */
public class UpdateSubjectCommand implements Command {

    private static final Logger LOG = Logger.getLogger(UpdateSubjectCommand.class);

    private SubjectDAO subjectDao = (SubjectDAO) DAOManager.getInstance().getDAO(Table.SUBJECT);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        String id = request.getParameter("id");
        LOG.trace("Request parameter: id --> " + id);

        String name = request.getParameter("name");
        LOG.trace("Request parameter: name --> " + name);

        Subject subject = new Subject();

        subject.setId(Long.valueOf(id));
        subject.setName(name);

        subjectDao.update(subject);

        LOG.trace("Update subject: subject --> " + subject);

        LOG.debug("Command finished");

        return Path.LIST_SUBJECTS_COMMAND + "&redirect";
    }
}
