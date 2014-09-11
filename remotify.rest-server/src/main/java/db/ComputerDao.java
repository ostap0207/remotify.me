package db;

import entities.Computer;
import org.springframework.stereotype.Component;

/**
 * .
 * User: Ostap
 * Date: 2/5/14
 * Time: 9:47 PM
 */
public interface ComputerDao extends GenericDao<Computer>  {

    public Computer loadByConnectionKey(String connectionKey);

    public Computer loadByUid(String uid);

    public Computer loadForUserById(String uid,String authKey);

}
