package ua.nure.bycheva.SummaryTask4.web.validation;

import javax.servlet.ServletRequest;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.SubjectDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Subject;
import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;
import ua.nure.bycheva.SummaryTask4.util.ValidationUtils;

/**
 * Subject form validator.
 */
public class SubjectFormValidator implements Validator{

    @Override
    public String getErrorURLByCommand(String commandName) {
        return commandName.startsWith("update") ? Path.EDIT_SUBJECT_PAGE : Path.PAGE_ADD_SUBJECT_PAGE;
    }

    @Override
    public boolean validate(ServletRequest servletRequest){
        boolean hasError = false;

        String parameter = servletRequest.getParameter("name");

        try {
            if(ValidationUtils.isNulableOrEmptyString(parameter)){
                servletRequest.setAttribute("name_error", MessageManager.ERR_FIELDS_CANNOT_BE_EMPTY);
                hasError = true;
            }else {
                Subject subject = ((SubjectDAO) DAOManager.getInstance().getDAO(Table.SUBJECT)).findByName(parameter);
                String paramId = servletRequest.getParameter("id");
                if (subject != null && (paramId == null || subject.getId() != Long.valueOf(paramId))) {
                    servletRequest.setAttribute("name_error", MessageManager.ERR_SUBJECT_WITH_THAT_NAME_EXISTS);
                    hasError = true;
                }
            }
        } catch (AppException e) {
            servletRequest.setAttribute("error_message",e.getMessage());
            hasError = true;
        }

        if(hasError){
            Subject subject = new Subject();
            if(servletRequest.getParameter("id") != null) {
                subject.setId(Long.valueOf(servletRequest.getParameter("id")));
            }
            subject.setName(parameter);
            servletRequest.setAttribute("subject", subject);
        }
        return hasError;
    }
}
