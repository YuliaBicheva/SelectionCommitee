package ua.nure.bycheva.SummaryTask4.db.dao;

import java.util.List;

import ua.nure.bycheva.SummaryTask4.db.entity.Faculty;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;

/**
 * Created by yulia on 13.08.16.
 */
public interface FacultyDAO extends CrudDAO<Faculty>{

    Faculty findByName(String name) throws DataBaseAccessException;

    List<Faculty> findAllNotExistsInSheets() throws DataBaseAccessException;
}
