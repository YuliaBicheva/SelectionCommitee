package ua.nure.bycheva.SummaryTask4.web.validation;

import javax.servlet.ServletRequest;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;
import ua.nure.bycheva.SummaryTask4.util.ValidationUtils;

/**
 * Login form validator.
 */
public class LoginFormValidator implements Validator{

    private static final Logger LOG = Logger.getLogger(LoginFormValidator.class);

    @Override
    public String getErrorURLByCommand(String commandName) {
        return Path.PAGE_LOGIN_PAGE;
    }

    @Override
    public boolean validate(ServletRequest servletRequest) {
        boolean hasError = false;

        String login = servletRequest.getParameter("login");
        if(ValidationUtils.isNulableOrEmptyString(login)){
            servletRequest.setAttribute("login_error", MessageManager.ERR_FIELDS_CANNOT_BE_EMPTY);
            hasError = true;
        }

        String password = servletRequest.getParameter("password");
        if(ValidationUtils.isNulableOrEmptyString(password)){
            servletRequest.setAttribute("password_error", MessageManager.ERR_FIELDS_CANNOT_BE_EMPTY);
            hasError = true;
        }

        return hasError;
    }
}
