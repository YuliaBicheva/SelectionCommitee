package ua.nure.bycheva.SummaryTask4.db.dao.impl;

import org.apache.log4j.Logger;
import ua.nure.bycheva.SummaryTask4.db.dao.AbstractDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.FacultyDAO;
import ua.nure.bycheva.SummaryTask4.db.Fields;
import ua.nure.bycheva.SummaryTask4.db.SQL;
import ua.nure.bycheva.SummaryTask4.db.entity.Faculty;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yulia on 13.08.16.
 */
public class FacultyDAOImpl extends AbstractDAO implements FacultyDAO {

    private static final Logger LOG = Logger.getLogger(FacultyDAOImpl.class);

    private static final String TABLENAME = "faculties";

    public FacultyDAOImpl(DataSource src) {
        super(src, TABLENAME);
    }

    public FacultyDAOImpl() {
        super(TABLENAME);
    }

    @Override
    public Faculty save(Faculty faculty) throws DataBaseAccessException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_SAVE_FACULTY, PreparedStatement.RETURN_GENERATED_KEYS);

            prepareToSave(st,faculty);

            rs = st.getGeneratedKeys();

            if(st.executeUpdate() > 0){
                rs = st.getGeneratedKeys();
                if(rs.next()){
                    faculty.setId(rs.getLong(1));
                }
            }
        }catch (SQLException e){
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_SAVE_ENTITY) + faculty;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st, con);
        }

        return faculty;
    }

    private void prepareToSave(PreparedStatement st, Faculty faculty) throws SQLException {
        int k = 0;
        st.setString(++k,faculty.getName());
        st.setInt(++k,faculty.getBudgetPlaces());
        st.setInt(++k,faculty.getTotalPlaces());
    }

    @Override
    public boolean update(Faculty faculty) throws DataBaseAccessException {
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_UPDATE_FACULTY);

            prepareToUpdate(st,faculty);

            return st.executeUpdate() > 0;
        }catch (SQLException e){
            rollback(con);
            e.printStackTrace();
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_UPDATE_ENTITY) + faculty;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(st,con);
        }

    }

    private void prepareToUpdate(PreparedStatement st, Faculty faculty) throws SQLException {
        int k = 0;
        st.setString(++k,faculty.getName());
        st.setInt(++k,faculty.getBudgetPlaces());
        st.setInt(++k,faculty.getTotalPlaces());
        st.setLong(++k, faculty.getId());
    }

    @Override
    public Faculty findById(Long id) throws DataBaseAccessException {
        Faculty faculty = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_FACULTY_BY_ID);
            st.setLong(1, id);
            rs = st.executeQuery();
            if(rs.next()){
                faculty = extractFaculty(rs);
            }
        }catch (SQLException e){
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ENTITY_BY_ID) + TABLENAME + ", id=" + id;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st, con);
        }
        return faculty;
    }

    private Faculty extractFaculty(ResultSet rs) throws SQLException {
        Faculty faculty = new Faculty();
        faculty.setId(rs.getLong(Fields.ENTITY_ID));
        faculty.setName(rs.getString(Fields.FACULTY_NAME));
        faculty.setBudgetPlaces(rs.getInt(Fields.FACULTY_BUDGET_PLACES));
        faculty.setTotalPlaces(rs.getInt(Fields.FACULTY_TOTAL_PLACES));
        return faculty;
    }

    @Override
    public List<Faculty> findAll() throws DataBaseAccessException {
        List<Faculty> faculties = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.createStatement();
            rs = st.executeQuery(SQL.SQL_FIND_FACULTIES);
            while(rs.next()){
                faculties.add(extractFaculty(rs));
            }
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ALL_ENTITIES) + TABLENAME;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }

        return faculties;
    }

    @Override
    public Faculty findByName(String name) throws DataBaseAccessException {
        Faculty faculty = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_FACULTY_BY_NAME);
            st.setString(1, name);
            rs = st.executeQuery();
            if(rs.next()){
                faculty = extractFaculty(rs);
            }
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_FACULTY_BY_NAME);
            LOG.error(msg,e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st, con);
        }
        return faculty;
    }

    @Override
    public List<Faculty> findAllNotExistsInSheets() throws DataBaseAccessException {
        List<Faculty> faculties = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.createStatement();
            rs = st.executeQuery(SQL.SQL_FIND_FACULTIES_NOT_EXISTS_IN_SHEETS);
            while(rs.next()){
                faculties.add(extractFaculty(rs));
            }
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_FACULTIES_WITHOUT_SHEET);
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st, con);
        }

        return faculties;
    }
}
