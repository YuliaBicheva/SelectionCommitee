package ua.nure.bycheva.SummaryTask4.db.dao;

import ua.nure.bycheva.SummaryTask4.db.bean.ApplicationMarkBean;
import ua.nure.bycheva.SummaryTask4.db.bean.EntrantMarkBean;
import ua.nure.bycheva.SummaryTask4.db.entity.Mark;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;

import java.util.List;

/**
 * Created by yulia on 15.08.16.
 */
public interface MarkDAO extends CrudDAO<Mark>{

    boolean save(List<Mark> marks) throws DataBaseAccessException;

    boolean update(List<Mark> marks) throws DataBaseAccessException;

    List<EntrantMarkBean> findAllEntrantMarks(Long userId) throws DataBaseAccessException;

    List<ApplicationMarkBean> findAllEntrantMarksForApplication(Long userId, Long facultyId) throws DataBaseAccessException;

    List<ApplicationMarkBean> findAllCertificateMarksForApplication(Long id) throws DataBaseAccessException;

    List<ApplicationMarkBean> findAllEntrantCertificateMarks(Long entrantId) throws DataBaseAccessException;
}
