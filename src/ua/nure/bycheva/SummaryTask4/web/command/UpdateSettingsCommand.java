package ua.nure.bycheva.SummaryTask4.web.command;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.Role;
import ua.nure.bycheva.SummaryTask4.db.dao.CertificateDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.EntrantDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.dao.UserDAO;
import ua.nure.bycheva.SummaryTask4.db.entity.Certificate;
import ua.nure.bycheva.SummaryTask4.db.entity.Entrant;
import ua.nure.bycheva.SummaryTask4.db.entity.User;
import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;

/**
 * Update settings command.
 */
public class UpdateSettingsCommand implements Command {

    private static final Logger LOG = Logger.getLogger(UpdateSettingsCommand.class);

    private UserDAO userDAO = (UserDAO) DAOManager.getInstance().getDAO(Table.USER);

    private EntrantDAO entrantDAO = (EntrantDAO) DAOManager.getInstance().getDAO(Table.ENTRANT);
    private CertificateDAO certificateDAO = (CertificateDAO) DAOManager.getInstance().getDAO(Table.CERTIFICATE);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String middleName = request.getParameter("middleName");
        String email = request.getParameter("email");

        HttpSession session = request.getSession();

        String locale = request.getParameter("localeToSet");

        Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", locale);

        session.setAttribute("userLocale", locale);

        User user = (User) session.getAttribute("user");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMiddleName(middleName);
        user.setEmail(email);
        user.setLocale(locale);
        if(session.getAttribute("userRole") == Role.ENTRANT) {
            String city = request.getParameter("city");
            String region = request.getParameter("region");
            String school = request.getParameter("school");

            Entrant entrant = (Entrant) session.getAttribute("entrant");
            entrant.setCity(city);
            entrant.setRegion(region);
            entrant.setSchool(school);

            entrantDAO.update(entrant);

            // get access to atestat file that is uploaded from client
            Part certificate;
            boolean needSave = false;
            Certificate entrantCert = certificateDAO.findById(entrant.getId());
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
                        if(entrantCert == null){
                            entrantCert = new Certificate();
                            needSave = true;
                        }
                        entrantCert.setFileName(user.getLastName().concat("_")
                                .concat(user.getLogin())
                                .concat(fileFormat));
                        entrantCert.setFileContent(buff);
                        entrantCert.setEntrantId(entrant.getId());

                        is.close();
                    }
                }
            } catch (IllegalStateException | IOException | ServletException e) {
                String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_UPLOAD_FILE);
                request.setAttribute("certificate_error",msg);
                LOG.error(msg,e);
                return Path.PAGE_SETTINGS_PAGE;
            }

            if(needSave) {
                certificateDAO.save(entrantCert);
            }else {
                certificateDAO.update(entrantCert);
            }
        }

        userDAO.update(user);

        LOG.debug("Command finished");
        return Path.SETTINGS_VIEW + "&redirect";
    }

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
}
