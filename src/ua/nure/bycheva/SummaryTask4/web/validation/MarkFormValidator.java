package ua.nure.bycheva.SummaryTask4.web.validation;

import javax.servlet.ServletRequest;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;
import ua.nure.bycheva.SummaryTask4.util.ValidationUtils;

/**
 * Mark form validator.
 */
public class MarkFormValidator implements Validator {

    @Override
    public String getErrorURLByCommand(String commandName) {
        return Path.ADD_MARK_PAGE;
    }

    @Override
    public boolean validate(ServletRequest servletRequest){
        boolean hasError = false;

        String paramSubjectId = servletRequest.getParameter("subjectId");

        if(ValidationUtils.isNulableOrEmptyString(paramSubjectId)) {
            servletRequest.setAttribute("subjectId_error", MessageManager.ERR_FIELDS_CANNOT_BE_EMPTY);
            hasError = true;
        }

        String paramPassedValue = servletRequest.getParameter("markValue");

        if(!ValidationUtils.isDecimal(paramPassedValue)){
            servletRequest.setAttribute("markValue_error", MessageManager.ERR_MUST_BE_DECIMAL_NO_CHARACTER);
            hasError = true;
        }else{
            String examType = servletRequest.getParameter("examType");
            double value = Double.valueOf(paramPassedValue);
            if(examType.equals("test") && (value < 0 || value > 200)) {
                servletRequest.setAttribute("markValue_error", MessageManager.ERR_FIELDS_VALUE_MUST_BE_BEETWEEN_0_AND_200);
                servletRequest.setAttribute("_error", MessageManager.ERR_FIELDS_VALUE_MUST_BE_BEETWEEN_0_AND_200);
                hasError = true;
            }

            if(examType.equals("certificate") && (value < 1 || value > 12)){
                servletRequest.setAttribute("markValue_error", MessageManager.ERR_FIELDS_VALUE_MUST_BE_BEETWEEN_1_AND_12);
                hasError = true;
            }
        }
        return hasError;
    }
}
