package ua.nure.bycheva.SummaryTask4.web.validation;

import java.util.Enumeration;

import javax.servlet.ServletRequest;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;
import ua.nure.bycheva.SummaryTask4.util.ValidationUtils;

/**
 * Settings form validator.
 */
public class SettingFormValidator implements Validator {


    @Override
    public String getErrorURLByCommand(String commandName) {
        return Path.SETTINGS_VIEW;
    }

    @Override
    public boolean validate(ServletRequest servletRequest){
        boolean hasError = false;

        Enumeration params = servletRequest.getParameterNames();
        while(params.hasMoreElements()){
            String paramName = (String) params.nextElement();
            String paramValue = servletRequest.getParameter(paramName);

            if(!paramName.equals("certificate") && ValidationUtils.isNulableOrEmptyString(paramValue)){
                servletRequest.setAttribute(paramName.concat("_error"), MessageManager.ERR_FIELDS_CANNOT_BE_EMPTY);
                hasError = true;
            }
        }

        return hasError;
    }
}
