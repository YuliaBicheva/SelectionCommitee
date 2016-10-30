package ua.nure.bycheva.SummaryTask4.db.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import ua.nure.bycheva.SummaryTask4.db.dao.CertificateDAO;
import ua.nure.bycheva.SummaryTask4.db.entity.Certificate;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;

/**
 * Created by yulia on 19.08.16.
 */
public class CertificateDAOImpl extends AbstractDAO<Certificate> implements CertificateDAO {

    private static final Logger LOG = Logger.getLogger(CertificateDAOImpl.class);

    private static final String TABLENAME = "certificates";

    public CertificateDAOImpl(DataSource src) {
        super(src, TABLENAME);
    }

    public CertificateDAOImpl() {
        super(TABLENAME);
    }

    @Override
    public Certificate save(Certificate certificate) throws DataBaseAccessException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        InputStream input = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_SAVE_CERTIFICATE,PreparedStatement.RETURN_GENERATED_KEYS);

            int k = 0;
            st.setLong(++k,certificate.getEntrantId());
            st.setString(++k,certificate.getFileName());
            input = new ByteArrayInputStream(certificate.getFileContent());
            st.setBinaryStream(++k, input, certificate.getFileContent().length);

            if(st.executeUpdate() > 0){
                rs = st.getGeneratedKeys();
                if(rs.next()){
                    certificate.setId(rs.getLong(1));
                }
            }
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_SAVE_ENTITY) + certificate;
            LOG.error(msg,e);
            e.printStackTrace();
            throw new DataBaseAccessException();
        }finally {
            if(input != null){
                try {
                    input.close();
                } catch (IOException e) {
                    LOG.error("Cannot close input stream",e);
                }
            }
            close(rs,st,con);
        }

        return certificate;
    }

    @Override
    public boolean update(Certificate certificate) throws DataBaseAccessException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        InputStream input = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_UPDATE_CERTIFICATE);

            int k = 0;
            st.setString(++k,certificate.getFileName());
            input = new ByteArrayInputStream(certificate.getFileContent());
            st.setBinaryStream(++k, input, certificate.getFileContent().length);
            st.setLong(++k,certificate.getEntrantId());

            return st.executeUpdate() > 0;
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_UPDATE_ENTITY) + certificate;
            LOG.error(msg,e);
            e.printStackTrace();
            throw new DataBaseAccessException();
        }finally {
            if(input != null){
                try {
                    input.close();
                } catch (IOException e) {
                    LOG.error("Cannot close input stream",e);
                }
            }
            close(rs,st,con);
        }
    }

    @Override
    public Certificate findById(Long entrantId) throws DataBaseAccessException {
        Certificate certificate = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_CERTIFICATE_BY_ENTRANT_ID);
            st.setLong(1, entrantId);
            rs = st.executeQuery();
            if(rs.next()){
                certificate = extractCertificate(rs);
            }
        }catch (SQLException e){
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ENTITY_BY_ID) + TABLENAME + ", id=" + entrantId;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }
        return certificate;
    }

    private Certificate extractCertificate(ResultSet rs) throws SQLException {
        Certificate certificate = new Certificate();
        certificate.setId(rs.getLong(Fields.ENTITY_ID));
        certificate.setEntrantId(rs.getLong(Fields.ENTRANT_ID));
        certificate.setFileName(rs.getString(Fields.CERT_FILE_NAME));
        InputStream is;
        try {
            is = rs.getBinaryStream(Fields.CERT_FILE_CONTENT);
            byte[] buff = new byte[400000];
            LOG.trace("buff length --> " + buff.length);
            while(is.read(buff) > 0);
            certificate.setFileContent(buff);
            is.close();
        } catch (IOException e) {
            LOG.warn("Cannot read certificate content");
        }
        return certificate;
    }

    @Override
    public List<Certificate> findAll() throws DataBaseAccessException {
        List<Certificate> certificates = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_CERTIFICATES);
            rs = st.executeQuery();
            while(rs.next()){
                certificates.add(extractCertificate(rs));
            }
        }catch (SQLException e){
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ALL_ENTITIES) + TABLENAME;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }
        return certificates;
    }
}
