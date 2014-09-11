package services.db;

import db.DeviceDao;
import entities.Device;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.ws.ServiceMode;

/**
 * .
 * User: Ostap
 * Date: 2/19/14
 * Time: 9:07 PM
 */

public interface DeviceService {

    public Device createForUser(Device device, User user);

    public Device getForUidAndUser(String uid, String user);

}
