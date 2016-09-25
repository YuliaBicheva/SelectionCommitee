package ua.nure.bycheva.SummaryTask4.web.command;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.Role;
import ua.nure.bycheva.SummaryTask4.db.dao.CertificateDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.SubjectDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Certificate;
import ua.nure.bycheva.SummaryTask4.db.entity.Entrant;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Settings view command.
 */
public class SettingsViewCommand implements Command {

    private static final Logger LOG = Logger.getLogger(SettingsViewCommand.class);

    private SubjectDAO subjectDao = (SubjectDAO) DAOManager.getInstance().getDAO(Table.SUBJECT);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException, IOException {
        LOG.debug("Command starts");

        HttpSession session = request.getSession();

        Role userRole = (Role) session.getAttribute("userRole");
        LOG.trace("Session attribute userRole: userRole --> " + userRole);

        if(userRole == Role.ENTRANT) {
            Entrant entrant = (Entrant) session.getAttribute("entrant");

            CertificateDAO certificateDAO = (CertificateDAO) DAOManager.getInstance().getDAO(Table.CERTIFICATE);
            Certificate certificate =  certificateDAO.findById(entrant.getId());

            if(certificate != null){
                FileOutputStream output = null;
                try {
                    String sessionId = session.getId();

                    String fileName = "/" + sessionId + "_" + certificate.getFileName();
                    String pathToWeb = request.getServletContext().getRealPath(Path.TMP_DIRECTORY_NAME);
                    String src = "/" + Path.TMP_DIRECTORY_NAME.concat(fileName);

                    File f = new File(pathToWeb + fileName);
                    output = new FileOutputStream(f);
                    output.write(certificate.getFileContent());
                    output.close();
                    request.setAttribute("fileName", src);
                    LOG.trace("fileName --> " + src);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                    if(output != null){
                        output.close();
                    }
                }
            }
        }

        LOG.debug("Command finished");
        return Path.PAGE_SETTINGS_PAGE;
    }
}
