package db;

import entities.Device;
import entities.User;

/**
 * .
 * User: Ostap
 * Date: 2/19/14
 * Time: 8:57 PM
 */

public interface DeviceDao extends GenericDao<Device> {

    public Device loadByUidAndUser(String uid, String user);

}
