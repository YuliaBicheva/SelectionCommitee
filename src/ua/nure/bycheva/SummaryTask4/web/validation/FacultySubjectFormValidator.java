package ua.nure.bycheva.SummaryTask4.web.validation;

import javax.servlet.ServletRequest;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;
import ua.nure.bycheva.SummaryTask4.util.ValidationUtils;

/**
 * Faculty subject form validator.
 */
public class FacultySubjectFormValidator implements Validator {

    @Override
    public String getErrorURLByCommand(String commandName) {
        return Path.ADD_FACULTY_SUBJECT_PAGE;
    }

    @Override
    public boolean validate(ServletRequest servletRequest){
        boolean hasError = false;

        String paramSubjectId = servletRequest.getParameter("subjectId");

        if(ValidationUtils.isNulableOrEmptyString(paramSubjectId)) {
            servletRequest.setAttribute("subjectId_error", MessageManager.ERR_FIELDS_CANNOT_BE_EMPTY);
            hasError = true;
        }

        return hasError;
    }
}
