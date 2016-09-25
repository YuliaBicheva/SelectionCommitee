package ua.nure.bycheva.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Select language command.
 */
public class SelectLanguageCommand implements Command {

    private static final Logger LOG = Logger.getLogger(SelectLanguageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        HttpSession session = request.getSession();

        String localeToSet = request.getParameter("localeToSet");

        Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", localeToSet);

        session.setAttribute("userLocale", localeToSet);

        LOG.debug("Command finished");
        return Path.MAIN_PAGE;
    }
}
