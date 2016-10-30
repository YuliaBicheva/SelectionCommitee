package ua.nure.bycheva.SummaryTask4.db.dao;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.db.dao.impl.ApplicationDAOImpl;
import ua.nure.bycheva.SummaryTask4.db.dao.impl.CertificateDAOImpl;
import ua.nure.bycheva.SummaryTask4.db.dao.impl.EntrantDAOImpl;
import ua.nure.bycheva.SummaryTask4.db.dao.impl.FacultyDAOImpl;
import ua.nure.bycheva.SummaryTask4.db.dao.impl.FacultySubjectDAOImpl;
import ua.nure.bycheva.SummaryTask4.db.dao.impl.MarkDAOImpl;
import ua.nure.bycheva.SummaryTask4.db.dao.impl.SheetDAOImpl;
import ua.nure.bycheva.SummaryTask4.db.dao.impl.SubjectDAOImpl;
import ua.nure.bycheva.SummaryTask4.db.dao.impl.UserDAOImpl;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;

/**
 * Created by yulia on 29.08.16.
 */
public class DAOManager {

    private static final Logger LOG = Logger.getLogger(DAOManager.class);

    private static DAOManager instance;

    public static DAOManager getInstance(){
        if(instance == null){
            instance = new DAOManager();
        }

        return  instance;
    }

    public Connection getConnection() throws DataBaseAccessException {
        Connection con = null;
        try{
            con = this.src.getConnection();
        } catch (Exception e) {
            LOG.error(MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_CONNECTION),e);
            throw new DataBaseAccessException(MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_CONNECTION),e);
        }
        return con;

    }

    public AbstractDAO getDAO(Table t){
        switch(t)
        {
            case USER:
                return new UserDAOImpl();
            case ENTRANT:
                return new EntrantDAOImpl();
            case FACULTY:
                return new FacultyDAOImpl();
            case SUBJECT:
                return new SubjectDAOImpl();
            case FACULTY_SUBJECTS:
                return new FacultySubjectDAOImpl();
            case MARK:
                return new MarkDAOImpl();
            case APPLICATION:
                return new ApplicationDAOImpl();
            case CERTIFICATE:
                return new CertificateDAOImpl();
            case SHEET:
                return new SheetDAOImpl();
            default:
                LOG.error("Trying to link to an unexistant table.");
                throw new RuntimeException("Trying to link to an unexistant table.");
        }
    }

    //Private
    private DataSource src;

    private DAOManager() {
        try
        {
            Context initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:/comp/env");
            src = (DataSource) envContext.lookup("jdbc/SUMTASK4");
        }
        catch(Exception e) {
            e.printStackTrace();
            LOG.warn(e.getMessage());
        }
    }
}
