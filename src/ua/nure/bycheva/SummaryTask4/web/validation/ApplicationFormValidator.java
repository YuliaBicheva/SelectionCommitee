package ua.nure.bycheva.SummaryTask4.web.validation;

import java.util.Enumeration;

import javax.servlet.ServletRequest;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;
import ua.nure.bycheva.SummaryTask4.util.ValidationUtils;

/**
 * Application form validator.
 */
public class ApplicationFormValidator implements Validator{

    private static final Logger LOG = Logger.getLogger(ApplicationFormValidator.class);

    @Override
    public String getErrorURLByCommand(String commandName) {
        return Path.APPLY_FACULTY_PAGE;
    }

    @Override
    public boolean validate(ServletRequest servletRequest) {
        boolean hasError = false;
        Enumeration params = servletRequest.getParameterNames();
        while(params.hasMoreElements()){
            String paramName = (String) params.nextElement();
            String paramValue = servletRequest.getParameter(paramName);

            if(paramName.startsWith("subject") && ((paramName.endsWith("test")||paramName.endsWith("certificate")))){
                LOG.debug(paramName + " --> " + paramValue);
                if(!ValidationUtils.isDecimal(paramValue)){
                    String[] pieces = paramName.split("_");
                    servletRequest.setAttribute(pieces[0].concat("_").concat(pieces[2]).concat("_error"), MessageManager.ERR_MUST_BE_DECIMAL_NO_CHARACTER);
                    hasError = true;
                }else {
                    String examType = paramName.split("_")[2];
                    double value = Double.valueOf(paramValue);
                    if(examType.equals("test") && (value < 0 || value > 200)) {
                        servletRequest.setAttribute("subjects_test_error", MessageManager.ERR_FIELDS_VALUE_MUST_BE_BEETWEEN_0_AND_200);
                        hasError = true;
                    }

                    if(examType.equals("certificate") && (value < 1 || value > 12)){
                        servletRequest.setAttribute("subject_certificate_error", MessageManager.ERR_FIELDS_VALUE_MUST_BE_BEETWEEN_1_AND_12);
                        hasError = true;
                    }
                }
            }
        }
        return hasError;
    }
}
