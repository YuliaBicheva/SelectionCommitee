package ua.nure.bycheva.SummaryTask4.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.db.Fields;
import ua.nure.bycheva.SummaryTask4.db.SQL;
import ua.nure.bycheva.SummaryTask4.db.dao.AbstractDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.SubjectDAO;
import ua.nure.bycheva.SummaryTask4.db.entity.Subject;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;

/**
 * Created by yulia on 15.08.16.
 */
public class SubjectDAOImpl extends AbstractDAO implements SubjectDAO {

    private static final Logger LOG = Logger.getLogger(SubjectDAOImpl.class);

    private static final String TABLENAME = "subjects";

    public SubjectDAOImpl(DataSource src) {
        super(src, TABLENAME);
    }

    public SubjectDAOImpl() {
        super(TABLENAME);
    }

    @Override
    public List<Subject> findAllNotExistInFaculty(Long facultyId) throws DataBaseAccessException {
        List<Subject> subjects = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        Connection con = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_SUBJECTS_NOT_EXIST_IN_FACULTY);
            st.setLong(1, facultyId);
            rs = st.executeQuery();
            while(rs.next()){
                subjects.add(extractSubject(rs));
            }
        }catch (SQLException e) {
            rollback(con);
            e.printStackTrace();
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_SUBJECTS_NOT_EXIST_IN_FACULTY);
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }
        return subjects;
    }

    @Override
    public List<Subject> findAllNotExistInMarks(Long entrantId, String examType) throws DataBaseAccessException {
        List<Subject> subjects = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        Connection con = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_SUBJECTS_NOT_EXIST_IN_ENTRANT_MARKS);
            st.setLong(1, entrantId);
            st.setString(2,examType);
            rs = st.executeQuery();
            while(rs.next()){
                subjects.add(executeSubject(rs));
            }
        }catch (SQLException e) {
            rollback(con);
            e.printStackTrace();
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_SUBJECTS_NOT_EXIST_IN_ENTRANT_MARKS);
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }
        return subjects;
    }

    @Override
    public List<Subject> findAllTestSubjectsWhichEntrantHaveNot(Long facultyId, Long entrantId) throws DataBaseAccessException {
        List<Subject> subjects = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        Connection con = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_TEST_SUBJECTS_WHICH_ENTRANT_HAVE_NOT_BY_FACULTY_ID);
            st.setLong(1, facultyId);
            st.setLong(2, entrantId);

            rs = st.executeQuery();

            while(rs.next()){
                subjects.add(extractSubject(rs));
            }
        }catch (SQLException e) {
            rollback(con);
            e.printStackTrace();
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_TEST_SUBJECTS_WHICH_ENTRANT_HAVE_NOT_BY_FACULTY_ID);
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }
        return subjects;
    }

    @Override
    public List<Subject> findAllCertificateSubjectsWhichEntrantNotHave(Long entrantId) throws DataBaseAccessException {
        List<Subject> subjects = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        Connection con = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_CERTIFICATE_SUBJECTS_WHICH_ENTRANT_HAVE_NOT);
            st.setLong(1, entrantId);
            rs = st.executeQuery();
            while(rs.next()){
                subjects.add(extractSubject(rs));
            }
        }catch (SQLException e) {
            rollback(con);
            e.printStackTrace();
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_CERTIFICATE_SUBJECTS_WHICH_ENTRANT_HAVE_NOT);
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }
        return subjects;
    }

    @Override
    public Subject findByName(String name) throws DataBaseAccessException {
        Subject subject = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Connection con = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_SUBJECT_BY_NAME);
            st.setString(1, name);
            rs = st.executeQuery();
            if(rs.next()){
                subject = executeSubject(rs);
            }
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_SUBJECT_BY_NAME);
            LOG.error(msg,e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }
        return subject;
    }

    @Override
    public List<Subject> findAll() throws DataBaseAccessException {
        List<Subject> subjects = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        Connection con = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_SUBJECTS);
            rs = st.executeQuery();
            while(rs.next()){
                subjects.add(extractSubject(rs));
            }
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ALL_ENTITIES) + TABLENAME;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }

        return subjects;
    }

    @Override
    public Subject save(Subject subject) throws DataBaseAccessException {
        PreparedStatement st = null;
        ResultSet rs = null;
        Connection con = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_SAVE_SUBJECT,PreparedStatement.RETURN_GENERATED_KEYS);

            prepareToSave(st,subject);

            if(st.executeUpdate() > 0){
                rs = st.getGeneratedKeys();
                if(rs.next()){
                    subject.setId(rs.getLong(1));
                }
            }
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_SAVE_ENTITY)+ subject;
            LOG.error(msg,e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }

        return subject;
    }

    private void prepareToSave(PreparedStatement st, Subject subject) throws SQLException {
        int k = 0;
        st.setString(++k,subject.getName());
    }

    @Override
    public boolean update(Subject subject) throws DataBaseAccessException {
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_UPDATE_SUBJECT);

            prepareToUpdate(st,subject);

            return st.executeUpdate() > 0;
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_UPDATE_ENTITY) + subject;
            LOG.error(msg,e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(st,con);
        }
    }

    private void prepareToUpdate(PreparedStatement st, Subject subject) throws SQLException {
        int k =0;
        st.setString(++k,subject.getName());
        st.setLong(++k,subject.getId());
    }

    @Override
    public Subject findById(Long id) throws DataBaseAccessException {
        Subject subject = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_SUBJECT_BY_ID);

            st.setLong(1,id);

            rs = st.executeQuery();

            if(rs.next()) {
                subject = executeSubject(rs);
            }
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ENTITY_BY_ID) + TABLENAME + ", id=" + id;
            LOG.error(msg,e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }

        return subject;
    }

    private Subject executeSubject(ResultSet rs) throws SQLException {
        Subject subject = new Subject();
        subject.setId(rs.getLong(Fields.ENTITY_ID));
        subject.setName(rs.getString(Fields.SUBJECT_NAME));
        return subject;
    }


    private Subject extractSubject(ResultSet rs) throws SQLException {
        Subject subject = new Subject();
        subject.setId(rs.getLong(Fields.ENTITY_ID));
        subject.setName(rs.getString(Fields.SUBJECT_NAME));
        return subject;
    }
}
