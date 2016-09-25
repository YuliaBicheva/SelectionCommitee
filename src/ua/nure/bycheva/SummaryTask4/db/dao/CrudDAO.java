package ua.nure.bycheva.SummaryTask4.db.dao;

import ua.nure.bycheva.SummaryTask4.db.entity.Entity;
import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;

import java.util.List;

/**
 * Created by yulia on 12.08.16.
 */
public interface CrudDAO<E extends Entity> {

    E save(E e) throws DataBaseAccessException;

    boolean update(E e) throws DataBaseAccessException;

    boolean delete(Long id) throws AppException;

    E findById(Long id) throws DataBaseAccessException;

    List<E> findAll() throws DataBaseAccessException;

    void clear() throws AppException;

}
