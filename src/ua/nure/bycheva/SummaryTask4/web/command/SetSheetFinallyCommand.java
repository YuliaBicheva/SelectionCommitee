package ua.nure.bycheva.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.ShetStatus;
import ua.nure.bycheva.SummaryTask4.db.bean.SheetEntrantBean;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.FacultyDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.SheetDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.dao.UserDAO;
import ua.nure.bycheva.SummaryTask4.db.entity.Faculty;
import ua.nure.bycheva.SummaryTask4.db.entity.Sheet;
import ua.nure.bycheva.SummaryTask4.db.entity.User;
import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;
import ua.nure.bycheva.SummaryTask4.util.FileManager;
import ua.nure.bycheva.SummaryTask4.util.MailHelper;

/**
 * Set finally status to the sheet. And send to the passed entrant information email.
 */
public class SetSheetFinallyCommand implements Command {

    private static final Logger LOG = Logger.getLogger(SetSheetFinallyCommand.class);

    private SheetDAO sheetDao = (SheetDAO) DAOManager.getInstance().getDAO(Table.SHEET);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException, IOException {
        LOG.debug("Command starts");

        Long sheetId = Long.valueOf(request.getParameter("id"));

        Sheet sheet = sheetDao.findById(sheetId);

        sheet.setStatusId(ShetStatus.FINALLY.ordinal());

        sheetDao.update(sheet);

        List<SheetEntrantBean> entrants = sheetDao.findAllEntrantsSheets(sheetId);

        Faculty faculty = ((FacultyDAO)DAOManager.getInstance().getDAO(Table.FACULTY)).findById(sheet.getFacultyId());

        try {
            sendOutSuccessPassed(entrants, faculty.getName());
        }catch (MessagingException e) {
            LOG.error(e.getMessage(),e);
            new AppException(e.getMessage(),e);
        }

        LOG.debug("Command finished");
        return Path.SHEET_PAGE+"&id=" + sheetId + "&redirect";
    }

    public void sendOutSuccessPassed(List<SheetEntrantBean> passedEntrants, String facultyName) throws DataBaseAccessException, MessagingException {
        Long[] entrantsId = new Long[passedEntrants.size()];
        int k = 0;
        for(SheetEntrantBean entrantBean: passedEntrants){
            entrantsId[k++] = entrantBean.getEntrantId();
        }
        List<User> users = ((UserDAO)DAOManager.getInstance().getDAO(Table.USER)).findAllByEntrantId(entrantsId);
        LOG.trace("Obtain users for mailing: users --> " + users);

        for(User user: users){
            String fullName = String.format("%s %s %s", user.getLastName(), user.getFirstName(), user.getMiddleName());
            MailHelper.sendMail(
                    user.getEmail(),
                    MessageManager.getInstance().getProperty(FileManager.MAIL_SUBJECT, user.getLocale()),
                    collectMsg(fullName, facultyName)
            );
        }

    }

    private String collectMsg(String fullName, String facultyName){
        StringBuilder msg = new StringBuilder();
        msg.append("Dear " + fullName + ",");
        msg.append(System.lineSeparator());
        msg.append("You have entered our University into ").append(facultyName);
        msg.append("Sincerely, ").append(System.lineSeparator()).append("The Univerdity");
        return msg.toString();
    }
}
