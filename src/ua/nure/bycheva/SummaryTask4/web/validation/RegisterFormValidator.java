package ua.nure.bycheva.SummaryTask4.web.validation;

import java.util.Enumeration;

import javax.servlet.ServletRequest;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;
import ua.nure.bycheva.SummaryTask4.util.ValidationUtils;

/**
 * Register form validator.
 */
public class RegisterFormValidator implements Validator{

    @Override
    public String getErrorURLByCommand(String commandName) {
        return Path.PAGE_REGISTER_PAGE;
    }

    @Override
    public boolean validate(ServletRequest servletRequest) {
        boolean hasError = false;
        Enumeration params = servletRequest.getParameterNames();
        while(params.hasMoreElements()){
            String paramName = (String) params.nextElement();
            String paramValue = servletRequest.getParameter(paramName);

            if(!paramName.equals("certificate") && ValidationUtils.isNulableOrEmptyString(paramValue)){
                hasError = setErrorAttribute(paramName, MessageManager.ERR_CANNOT_BE_EMPTY,servletRequest);
            }else if(paramName.equals("password") && !ValidationUtils.isPasswordsEquals(servletRequest.getParameterValues("password"))) {
                hasError = setErrorAttribute(paramName, MessageManager.ERR_PASSWORDS_SHOULD_BE_EQUAL, servletRequest);
            }else if(paramName.equals("password") && !ValidationUtils.isPasswordCorrect(servletRequest.getParameterValues("password")[0])){
                servletRequest.setAttribute("password_error", MessageManager.ERR_PASSWORD_MUST_HAVE_4_TILL_10_ELEMENTS);
                hasError = true;
            }else if(paramName.equals("email") && !ValidationUtils.isEmail(paramValue)){
                hasError = setErrorAttribute(paramName, MessageManager.ERR_EMAIL_MUST_BE_CORRECT, servletRequest);
            }else{
                servletRequest.setAttribute(paramName,servletRequest.getParameter(paramName));
            }
        }

        return hasError;
    }

    private boolean setErrorAttribute(String paramName, String errorMessage, ServletRequest servletRequest) {
        servletRequest.setAttribute(paramName + "_error", errorMessage);
        return true;
    }
}
