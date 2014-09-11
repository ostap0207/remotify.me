package db;

import entities.Session;

/**
 * .
 * User: Ostap
 * Date: 5/25/14
 * Time: 8:30 PM
 */
public interface SessionDao extends GenericDao<Session> {

    public Session loadByUid(String uid);

    public Session loadLatestForComputer(int computerId);

    public Session loadLatestForComputerByUid(String uid);

}
