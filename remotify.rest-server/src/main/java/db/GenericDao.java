package db;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Ostap
 * Date: 2/4/14
 * Time: 12:20 PM
 * To change this template use File | Settings | File Templates.
 */

public interface GenericDao< T > {
    /**
     * Method that returns the number of entries from a table that meet some
     * criteria (where clause params)
     *
     * @param params
     *            sql parameters
     * @return the number of records meeting the criteria
     */
    long countAll(Map params);

    T create(T t);

    void delete(Object id);

    T find(Object id);

    T update(T t);
}