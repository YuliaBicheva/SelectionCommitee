package ua.nure.bycheva.SummaryTask4.db.dao.impl;

import org.apache.log4j.Logger;
import ua.nure.bycheva.SummaryTask4.db.bean.FacultySubjectBean;
import ua.nure.bycheva.SummaryTask4.db.dao.AbstractDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.FacultySubjectDAO;
import ua.nure.bycheva.SummaryTask4.db.Fields;
import ua.nure.bycheva.SummaryTask4.db.SQL;
import ua.nure.bycheva.SummaryTask4.db.entity.FacultySubject;
import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yulia on 19.08.16.
 */
public class FacultySubjectDAOImpl extends AbstractDAO implements FacultySubjectDAO {

    private static final Logger LOG = Logger.getLogger(FacultySubjectDAOImpl.class);

    private static final String TABLENAME = "faculties_subjects";

    public FacultySubjectDAOImpl(DataSource src) {
        super(src, TABLENAME);
    }

    public FacultySubjectDAOImpl() {
        super(TABLENAME);
    }

    @Override
    public FacultySubject save(FacultySubject facultySubject) throws DataBaseAccessException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_SAVE_FACULTY_SUBJECT, PreparedStatement.RETURN_GENERATED_KEYS);

            prepareToSave(st,facultySubject);

            if(st.executeUpdate() > 0){
                rs = st.getGeneratedKeys();
                if(rs.next()){
                    facultySubject.setId(rs.getLong(1));
                }
            }
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_SAVE_ENTITY) + facultySubject;
            LOG.error(msg,e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }

        return facultySubject;
    }

    private void prepareToSave(PreparedStatement st, FacultySubject facultySubject) throws SQLException {
        int k = 0;
        st.setLong(++k,facultySubject.getFacultyId());
        st.setLong(++k,facultySubject.getSubjectId());
        st.setDouble(++k,facultySubject.getRatio());
    }

    @Override
    public boolean update(FacultySubject facultySubject) throws DataBaseAccessException {
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_UPDATE_FACULTY_SUBJECT);

            prepareToUpdate(st,facultySubject);

            return st.executeUpdate() > 0;
        }catch (SQLException e){
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_UPDATE_ENTITY) + facultySubject;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(st,con);
        }
    }

    @Override
    public boolean update(List<FacultySubject> facultySubjects) throws DataBaseAccessException {
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_UPDATE_FACULTY_SUBJECT);

            for(FacultySubject facultySubject: facultySubjects){
                prepareToUpdate(st,facultySubject);
                st.addBatch();
            }

            int[] count = st.executeBatch();
            con.commit();

            return count.length == facultySubjects.size();
        }catch (SQLException e){
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_UPDATE_FACULTY_SUBJECTS_LIST);
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(st,con);
        }
    }

    private void prepareToUpdate(PreparedStatement st, FacultySubject facultySubject) throws SQLException {
        int k = 0;
        st.setDouble(++k,facultySubject.getRatio());
        st.setLong(++k,facultySubject.getFacultyId());
        st.setLong(++k,facultySubject.getSubjectId());
    }

    @Override
    public boolean delete(Long id) throws AppException {
        throw new AppException("Doesn't support method delete by id");
    }

    @Override
    public boolean delete(Long facultyId, Long subjectId) throws DataBaseAccessException {
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_DELETE_FACULTY_SUBJECT);

            st.setLong(1,facultyId);
            st.setLong(2,subjectId);

            return st.executeUpdate() > 0;
        }catch (SQLException e){
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_DELETE_SUBJECT_FROM_FACULTY);
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(st,con);
        }
    }

    @Override
    public FacultySubject findById(Long id) throws DataBaseAccessException {
        throw new UnsupportedOperationException("Find by id method");
    }

    @Override
    public List<FacultySubject> findAll() throws DataBaseAccessException {
        throw new UnsupportedOperationException("FaculySubjectDAO FindAll method");
    }

    @Override
    public List<FacultySubjectBean> findAllFacultySubjectsBeans(Long id) throws DataBaseAccessException {
        List<FacultySubjectBean> facultySubjectBeans = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_FACULTY_SUBJECTS_BEAN_BY_FACULTY_ID);

            st.setLong(1,id);

            rs = st.executeQuery();
            while(rs.next()){
                facultySubjectBeans.add(extractFacultySubjectBean(rs));
            }
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ALL_FACULTY_SUBJECTS);
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }

        return facultySubjectBeans;
    }

    private FacultySubjectBean extractFacultySubjectBean(ResultSet rs) throws SQLException {
        FacultySubjectBean facultySubjectBean = new FacultySubjectBean();
        facultySubjectBean.setFacultyId(rs.getLong(Fields.FACULTY_ID));
        facultySubjectBean.setSubjectId(rs.getLong(Fields.SUBJECT_ID));
        facultySubjectBean.setSubjectName(rs.getString(Fields.SUBJECT_NAME));
        facultySubjectBean.setRatio(rs.getDouble(Fields.FACT_SUBJ_RATIO));
        return facultySubjectBean;
    }
}
