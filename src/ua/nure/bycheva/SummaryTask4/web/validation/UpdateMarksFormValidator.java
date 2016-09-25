package ua.nure.bycheva.SummaryTask4.web.validation;

import java.util.Enumeration;

import javax.servlet.ServletRequest;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;
import ua.nure.bycheva.SummaryTask4.util.ValidationUtils;

/**
 * Marks updating form validator.
 */
public class UpdateMarksFormValidator implements Validator {
    private static final Logger LOG = Logger.getLogger(ApplicationFormValidator.class);

    @Override
    public String getErrorURLByCommand(String commandName) {
        return Path.LIST_MARKS_PAGE;
    }

    @Override
    public boolean validate(ServletRequest servletRequest) {
        boolean hasError = false;
        Enumeration params = servletRequest.getParameterNames();
        while(params.hasMoreElements()){
            String paramName = (String) params.nextElement();
            String paramValue = servletRequest.getParameter(paramName);

            if(paramName.startsWith("mark") && (paramName.endsWith("test")|| paramName.endsWith("certificate"))){
                LOG.debug(paramName + " --> " + paramValue);
                String[] pieces = paramName.split("_");
                String examType = pieces[2];
                if(!ValidationUtils.isDecimal(paramValue)){
                    servletRequest.setAttribute("markValue_error", MessageManager.ERR_MUST_BE_DECIMAL_NO_CHARACTER);
                    hasError = true;
                }else{
                    double value = Double.valueOf(paramValue);
                    if(examType.equals("test") && (value < 0 || value > 200)) {
                        servletRequest.setAttribute("markValue_test_error", MessageManager.ERR_FIELDS_VALUE_MUST_BE_BEETWEEN_0_AND_200);
                        hasError = true;
                    }

                    if(examType.equals("certificate") && (value < 1 || value > 12)){
                        servletRequest.setAttribute("markValue_cert_error", MessageManager.ERR_FIELDS_VALUE_MUST_BE_BEETWEEN_1_AND_12);
                        hasError = true;
                    }
                }
            }
        }
        return hasError;
    }
}
