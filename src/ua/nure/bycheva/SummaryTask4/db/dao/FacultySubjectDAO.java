package ua.nure.bycheva.SummaryTask4.db.dao;

import ua.nure.bycheva.SummaryTask4.db.bean.FacultySubjectBean;
import ua.nure.bycheva.SummaryTask4.db.entity.FacultySubject;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;

import java.util.List;

/**
 * Created by yulia on 19.08.16.
 */
public interface FacultySubjectDAO extends CrudDAO<FacultySubject> {

    boolean update(List<FacultySubject> facultySubjects) throws DataBaseAccessException;

    boolean delete(Long facultyId, Long subjectId) throws DataBaseAccessException;

    List<FacultySubjectBean> findAllFacultySubjectsBeans(Long id) throws DataBaseAccessException;
}
