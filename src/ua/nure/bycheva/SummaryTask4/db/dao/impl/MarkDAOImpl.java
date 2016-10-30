package ua.nure.bycheva.SummaryTask4.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.db.Fields;
import ua.nure.bycheva.SummaryTask4.db.SQL;
import ua.nure.bycheva.SummaryTask4.db.bean.ApplicationMarkBean;
import ua.nure.bycheva.SummaryTask4.db.bean.EntrantMarkBean;
import ua.nure.bycheva.SummaryTask4.db.dao.AbstractDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.MarkDAO;
import ua.nure.bycheva.SummaryTask4.db.entity.Mark;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;

/**
 * Created by yulia on 15.08.16.
 */
public class MarkDAOImpl extends AbstractDAO implements MarkDAO {

    private static final Logger LOG = Logger.getLogger(MarkDAOImpl.class);

    private static final String TABLENAME = "marks";

    public MarkDAOImpl(DataSource src) {
        super(src, TABLENAME);
    }

    public MarkDAOImpl() {
        super(TABLENAME);
    }

    @Override
    public Mark save(Mark mark) throws DataBaseAccessException {
        PreparedStatement st = null;
        Connection con = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_SAVE_MARK,PreparedStatement.RETURN_GENERATED_KEYS);

            prepareToSave(st,mark);

            if(st.executeUpdate() > 0){
                rs = st.getGeneratedKeys();
                if(rs.next()){
                    mark.setId(rs.getLong(1));
                }
            }
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_SAVE_ENTITY) + mark;
            LOG.error(msg,e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }

        return mark;
    }

    @Override
    public boolean save(List<Mark> marks) throws DataBaseAccessException {
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = getConnection();
            con.setAutoCommit(false);
            st = con.prepareStatement(SQL.SQL_SAVE_MARK);

            for(Mark mark: marks){
                prepareToSave(st,mark);
                st.addBatch();
            }

            int[] count = st.executeBatch();

            con.commit();

            return count.length == marks.size();
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_SAVE_MARKS_LIST);
            LOG.error(msg,e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(st,con);
        }
    }

    @Override
    public boolean update(Mark mark) throws DataBaseAccessException {
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_UPDATE_MARK);

            prepareToUpdate(st,mark);

            return st.executeUpdate() > 0;
        }catch (SQLException e){
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_UPDATE_ENTITY) + mark;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(st,con);
        }
    }

    @Override
    public boolean update(List<Mark> marks) throws DataBaseAccessException {
        PreparedStatement st = null;
        Connection con = null;
        try{
            con = getConnection();
            con.setAutoCommit(false);
            st = con.prepareStatement(SQL.SQL_UPDATE_MARK);

            for(Mark mark: marks){
                prepareToUpdate(st,mark);
                st.addBatch();
            }

            int[] count = st.executeBatch();

            con.commit();

            return count.length == marks.size();
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_UPDATE_MARKS_LIST);
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(st,con);
        }
    }

    @Override
    public Mark findById(Long id) throws DataBaseAccessException {
        Mark mark = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_MARK_BY_ID);
            st.setLong(1, id);
            rs = st.executeQuery();
            if(rs.next()){
                mark = extractMark(rs);
            }
        }catch (SQLException e){
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ENTITY_BY_ID) + TABLENAME + ", id=" + id;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }
        return mark;
    }


    @Override
    public List<Mark> findAll() throws DataBaseAccessException {
        List<Mark> marks = new ArrayList<>();Statement st = null;
        Connection con = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.createStatement();
            rs = st.executeQuery(SQL.SQL_FIND_MARKS);
            while(rs.next()){
                marks.add(extractMark(rs));
            }
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ALL_ENTITIES) + TABLENAME;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }

        return marks;
    }

    @Override
    public List<EntrantMarkBean> findAllEntrantMarks(Long entrantId) throws DataBaseAccessException {
        List<EntrantMarkBean> entrantMarks = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            con.setAutoCommit(false);
            st = con.prepareStatement(SQL.SQL_FIND_ENTRANT_MARKS_BEANS);

            st.setLong(1,entrantId);

            rs = st.executeQuery();

            while(rs.next()){
                entrantMarks.add(executeEntrantMarksBean(rs));
            }

        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ALL_ENTRANT_MARKS);
            LOG.error(msg,e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }


        return entrantMarks;
    }

    @Override
    public List<ApplicationMarkBean> findAllEntrantMarksForApplication(Long userId, Long facultyId) throws DataBaseAccessException {
        Connection con = null;
        List<ApplicationMarkBean> entrantMarks = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            con.setAutoCommit(false);
            st = con.prepareStatement(SQL.SQL_FIND_MARKS_BEANS_FOR_APPLICATION);

            st.setLong(1,userId);
            st.setLong(2,facultyId);

            rs = st.executeQuery();

            while(rs.next()){
                entrantMarks.add(executeApplicationMarksBean(rs));
            }

        }catch (SQLException e) {
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ALL_ENTRANT_MARKS_FOR_APPLICATION);
            LOG.error(msg,e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }


        return entrantMarks;
    }

    @Override
    public List<ApplicationMarkBean> findAllCertificateMarksForApplication(Long id) throws DataBaseAccessException {
        Connection con = null;
        List<ApplicationMarkBean> entrantMarks = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            con.setAutoCommit(false);
            st = con.prepareStatement(SQL.SQL_FIND_CERTIFICATE_MARKS_BEANS);

            st.setLong(1,id);

            rs = st.executeQuery();

            while(rs.next()){
                entrantMarks.add(executeApplicationMarksBean(rs));
            }

        }catch (SQLException e) {
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ALL_CERTIFICATE_MARKS_FOR_APPLICATION);
            LOG.error(msg,e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }


        return entrantMarks;
    }

    @Override
    public List<ApplicationMarkBean> findAllEntrantCertificateMarks(Long entrantId) throws DataBaseAccessException {
        Connection con = null;
        List<ApplicationMarkBean> entrantMarks = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            con.setAutoCommit(false);
            st = con.prepareStatement(SQL.SQL_FIND_ENTRANT_CERTIFICATE_MARKS_BEANS);

            st.setLong(1,entrantId);

            rs = st.executeQuery();

            while(rs.next()){
                entrantMarks.add(executeApplicationMarksBean(rs));
            }

        }catch (SQLException e) {
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ALL_CERTIFICATE_MARKS_FOR_APPLICATION);
            LOG.error(msg,e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }


        return entrantMarks;
    }

    private ApplicationMarkBean executeApplicationMarksBean(ResultSet rs) throws SQLException {
        ApplicationMarkBean appMarkBean = new ApplicationMarkBean();
        appMarkBean.setSubjectName(rs.getString(Fields.SUBJECT_NAME));
        appMarkBean.setMarkValue(rs.getDouble(Fields.MARK_VALUE));
        appMarkBean.setExamType(rs.getString(Fields.MARK_EXAM_TYPE));
        return appMarkBean;
    }

    private Mark extractMark(ResultSet rs) throws SQLException {
        Mark mark = new Mark();
        mark.setId(rs.getLong(Fields.ENTITY_ID));
        mark.setSubjectId(rs.getLong(Fields.SUBJECT_ID));
        mark.setEntrantId(rs.getLong(Fields.ENTRANT_ID));
        mark.setMarkValue(rs.getDouble(Fields.MARK_VALUE));
        mark.setExamType(rs.getString(Fields.MARK_EXAM_TYPE));
        return mark;
    }

    private void prepareToSave(PreparedStatement st, Mark mark) throws SQLException {
        int k = 0;
        st.setLong(++k,mark.getSubjectId());
        st.setLong(++k,mark.getEntrantId());
        st.setDouble(++k,mark.getMarkValue());
        st.setString(++k,mark.getExamType());
    }

    private void prepareToUpdate(PreparedStatement st, Mark mark) throws SQLException {
        int k=0;
        st.setDouble(++k,mark.getMarkValue());
        st.setLong(++k,mark.getId());
    }

    private EntrantMarkBean executeEntrantMarksBean(ResultSet rs) throws SQLException {
        EntrantMarkBean entrantMarkBean = new EntrantMarkBean();
        entrantMarkBean.setId(rs.getLong(Fields.ENTITY_ID));
        entrantMarkBean.setSubjectName(rs.getString(Fields.SUBJECT_NAME));
        entrantMarkBean.setMarkValue(rs.getDouble(Fields.MARK_VALUE));
        entrantMarkBean.setExamType(rs.getString(Fields.MARK_EXAM_TYPE));
        return entrantMarkBean;
    }

}
