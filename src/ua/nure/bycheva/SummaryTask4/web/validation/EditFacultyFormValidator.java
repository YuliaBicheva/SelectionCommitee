package ua.nure.bycheva.SummaryTask4.web.validation;

import java.util.Enumeration;

import javax.servlet.ServletRequest;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;
import ua.nure.bycheva.SummaryTask4.util.ValidationUtils;

/**
 * Faculty edition form validator.
 */
public class EditFacultyFormValidator implements Validator {

    private static final Logger LOG = Logger.getLogger(EditFacultyFormValidator.class);
    @Override
    public String getErrorURLByCommand(String commandName) {
        return Path.EDIT_FACULTY_PAGE;
    }

    @Override
    public boolean validate(ServletRequest servletRequest){
        boolean hasError = false;
//        new AddFacultyFormValidator().validate(servletRequest);

        Enumeration params = servletRequest.getParameterNames();
        while(params.hasMoreElements()){
            String paramName = (String) params.nextElement();
            String paramValue = servletRequest.getParameter(paramName);

            if(paramName.startsWith("subject_")){
                LOG.trace("Obtain parameter which start with 'subject_'");
                String subjectParam = paramName.split("_")[2];
                if(ValidationUtils.isNulableOrEmptyString(paramValue)){
                    servletRequest.setAttribute(subjectParam.concat("_error"), MessageManager.ERR_FIELDS_CANNOT_BE_EMPTY);
                    hasError = true;
                    break;
                }else{
                    if(ValidationUtils.isDecimal(paramValue)){
                        double value = Double.valueOf(paramValue);
                        if(subjectParam.equals("ratio")){
                            if(value < 0 || value > 1){
                                servletRequest.setAttribute(subjectParam.concat("_error"), MessageManager.ERR_FIELDS_VALUE_MUST_BE_BEETWEEN_0_AND_1);
                                hasError = true;
                            }
                        }
                    }
                }
            }
        }
        return hasError;
    }
}
