package ua.nure.bycheva.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.Role;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.EntrantDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.dao.UserDAO;
import ua.nure.bycheva.SummaryTask4.db.entity.Entrant;
import ua.nure.bycheva.SummaryTask4.db.entity.User;
import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;
import ua.nure.bycheva.SummaryTask4.util.PasswordUtil;

/**
 * Login command.
 *
 */
public class LoginCommand implements Command {

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        String login = request.getParameter("login");
        LOG.trace("Request parameter: login --> " + login);

        String password = request.getParameter("password");
        LOG.trace("Request parameter: password --> " + password);

        User user = ((UserDAO) DAOManager.getInstance().getDAO(Table.USER)).findByLogin(login);
        LOG.trace("Found in DB: user --> " + user);

        if(user == null || !PasswordUtil.verifyPass(user.getPassword(),password)){
            request.setAttribute("fail_error", MessageManager.ERR_CANNOT_LOGIN_WITH_CURRENT_LOGIN_AND_PASSWORD);
            return Path.PAGE_LOGIN_PAGE;
        }

        HttpSession session = request.getSession();

        session.setAttribute("user", user);
        LOG.trace("Set the session attribute: user --> " + user);

        Role userRole = Role.getRole(user);
        LOG.trace("userRole --> " + userRole);

        session.setAttribute("userRole", userRole);
        LOG.trace("Set the session attribute: userRole --> " + userRole);

        String userLocale = user.getLocale();
        LOG.trace("default locale --> " + userLocale);

        Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLocale);

        session.setAttribute("userLocale", userLocale);
        LOG.trace("Set the session attribute: userLocale --> " + userLocale);

        LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());

        if(userRole == Role.ENTRANT){
            Entrant entrant = ((EntrantDAO) DAOManager.getInstance().getDAO(Table.ENTRANT)).findByUserId(user.getId());
            session.setAttribute("entrant", entrant);
        }

        LOG.debug("Command finished");
        return Path.MAIN_PAGE + "&redirect";
    }
}
