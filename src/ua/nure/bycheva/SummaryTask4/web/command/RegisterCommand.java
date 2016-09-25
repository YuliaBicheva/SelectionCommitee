package ua.nure.bycheva.SummaryTask4.web.command;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.Role;
import ua.nure.bycheva.SummaryTask4.db.Status;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.dao.UserDAO;
import ua.nure.bycheva.SummaryTask4.db.entity.Certificate;
import ua.nure.bycheva.SummaryTask4.db.entity.Entrant;
import ua.nure.bycheva.SummaryTask4.db.entity.User;
import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;
import ua.nure.bycheva.SummaryTask4.util.FileManager;
import ua.nure.bycheva.SummaryTask4.util.MailHelper;
import ua.nure.bycheva.SummaryTask4.util.PasswordUtil;

/**
 * Register command.
 *
 */
public class RegisterCommand implements Command {

    private static final Logger LOG = Logger.getLogger(RegisterCommand.class);

    private UserDAO userDAO = (UserDAO) DAOManager.getInstance().getDAO(Table.USER);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException{
        LOG.debug("Command starts");

        String login = request.getParameter("login");
        LOG.trace("Request parameter: login --> " + login);

        if(((UserDAO) DAOManager.getInstance().getDAO(Table.USER)).findByLogin(login) != null){
            Enumeration params = request.getParameterNames();
            while(params.hasMoreElements()){
                String paramName = (String) params.nextElement();
                String paramValue = request.getParameter(paramName);

                request.setAttribute(paramName,paramValue);
            }
            request.setAttribute("login_error", MessageManager.ERR_USER_WITH_THAT_LOGIN_EXISTS);
            return Path.PAGE_REGISTER_PAGE;
        }
        LOG.trace("Current login doesn't exists in database: login --> " + login);

        String password = request.getParameter("password");
        LOG.trace("Request password: password --> " + password);

        String email = request.getParameter("email");
        LOG.trace("Request parameter: email --> " + email);

        String firstName = request.getParameter("firstName");
        LOG.trace("Request parameter: firstName --> " + firstName);

        String lastName = request.getParameter("lastName");
        LOG.trace("Request parameter: lastName --> " + lastName);

        String middleName = request.getParameter("middleName");
        LOG.trace("Request parameter: middleName --> " + middleName);

        String city = request.getParameter("city");

        String region = request.getParameter("region");

        String school  = request.getParameter("school");

        String userLocale = request.getParameter("userLocaleToSet");
        LOG.trace("Request parameter: userLocale --> " + userLocale);

        User user = new User();

        user.setLogin(login);
        try {
            user.setPassword(PasswordUtil.hash(password));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new AppException(e.getMessage(), e);
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMiddleName(middleName);
        user.setEmail(email);
        user.setLocale(userLocale);
        user.setRoleId(Role.ENTRANT.ordinal());

        Entrant entrant = new Entrant();
        entrant.setCity(city);
        entrant.setRegion(region);
        entrant.setSchool(school);
        entrant.setStatusId(Status.ACTIVE.ordinal());

        // get access to atestat file that is uploaded from client
        Part certificate;
        Certificate entrantCert = null;
        try {
            certificate = request.getPart("certificate");

            if (certificate != null) {
                String fileName = getFileName(certificate);
                if(fileName != null&&!fileName.isEmpty()) {
                    String fileFormat = fileName.substring(fileName.lastIndexOf('.'));
                    InputStream is = certificate.getInputStream();
                    byte[] buff = new byte[is.available()];
                    LOG.trace("File size --> " + is.available());
                    is.read(buff);

                    LOG.trace("File uploaded");
                    entrantCert = new Certificate();
                    entrantCert.setFileName(
                                    user.getLastName()
                                    .concat("_")
                                    .concat(user.getLogin())
                                    .concat(fileFormat));
                    entrantCert.setFileContent(buff);

                    is.close();
                }
            }
        } catch (IllegalStateException | IOException | ServletException e) {
            String msg = MessageManager.ERR_CANNOT_UPLOAD_FILE;
            request.setAttribute("certificate_error",msg);
            LOG.error(msg,e);
            return Path.PAGE_REGISTER_PAGE;
        }

        LOG.trace("User --> " + user);
        LOG.trace("Entrant --> " + entrant);
        LOG.trace("Certificate --> " + entrantCert);
        boolean result = userDAO.register(user, entrant, entrantCert);
        LOG.info("Registration succesful --> " + result);

        try {
            sendSuccessRegistration(user, password);
        }catch (MessagingException e) {
            LOG.error("Error send succes message to the user " + user);
        }

        LOG.debug("Command finished");
        return Path.PAGE_SUCCESS_PAGE+"?registration_success="+ FileManager.SUCCESS_REGISTRATION+"&redirect";
    }

    /**
     * Get file user native file name
     * @param part
     * @return
     */
    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        LOG.info("Part Header = " + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    /**
     * Send to the user mail with success registration info
     * @param user
     * @param password
     * @throws DataBaseAccessException
     * @throws MessagingException
     */
    public void sendSuccessRegistration(User user, String password) throws DataBaseAccessException, MessagingException {
        String fullName = String.format("%s %s %s", user.getLastName(), user.getFirstName(), user.getMiddleName());
        MailHelper.sendMail(
                user.getEmail(),
                MessageManager.getInstance().getProperty(FileManager.MAIL_SUBJECT_REGISTER, user.getLocale()),
                collectMsg(fullName, user.getLogin(), password)
        );
    }

    /**
     * Create message for mailing about sccessfull registration
     * @param fullName user full name
     * @param login user login
     * @param pass user password
     * @return
     */
    private String collectMsg(String fullName, String login, String pass){
        StringBuilder msg = new StringBuilder();
        msg.append("Dear " + fullName + ",").append(System.lineSeparator());
        msg.append("You have registrated on our official site of University.").append(System.lineSeparator());
        msg.append("Your login: ").append(login).append(System.lineSeparator()).append(System.lineSeparator());
        msg.append("Your password: ").append(pass);
        msg.append("Sincerely, ").append(System.lineSeparator()).append("The University");
        return msg.toString();
    }
}
