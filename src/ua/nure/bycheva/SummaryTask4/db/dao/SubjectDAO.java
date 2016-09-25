package ua.nure.bycheva.SummaryTask4.db.dao;

import ua.nure.bycheva.SummaryTask4.db.entity.Subject;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;

import java.util.List;

/**
 * Created by yulia on 15.08.16.
 */
public interface SubjectDAO extends CrudDAO<Subject>{

    List<Subject> findAllNotExistInFaculty(Long facultyId) throws DataBaseAccessException;

    List<Subject> findAllNotExistInMarks(Long entrantId, String examType) throws DataBaseAccessException;

    List<Subject> findAllTestSubjectsWhichEntrantHaveNot(Long facultyId, Long entrantId) throws DataBaseAccessException;

    List<Subject> findAllCertificateSubjectsWhichEntrantNotHave(Long entrantId) throws DataBaseAccessException;

    Subject findByName(String name) throws DataBaseAccessException;
}
