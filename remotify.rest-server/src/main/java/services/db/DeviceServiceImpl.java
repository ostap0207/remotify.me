package services.db;

import db.DeviceDao;
import entities.Device;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * .
 * User: Ostap
 * Date: 2/19/14
 * Time: 9:07 PM
 */

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    @Override
    @Transactional
    public Device createForUser(Device device, User user) {
        device.setUser(user);
        device = deviceDao.create(device);
        return device;
    }

    @Override
    @Transactional(readOnly = true)
    public Device getForUidAndUser(String uid, String user) {
        return deviceDao.loadByUidAndUser(uid,user);
    }

}
