package db;

import entities.Device;
import entities.User;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

/**
 * .
 * User: Ostap
 * Date: 2/19/14
 * Time: 8:58 PM
 */

@Component("deviceDao")
public class DeviceDaoImpl extends GenericDaoImpl<Device> implements DeviceDao {

    @Override
    public Device loadByUidAndUser(String uid, String authKey) {
        Query query = this.em
                .createQuery("select d FROM Device d where d.uid = :uid and user.authKey = :authKey");
        query.setParameter("uid", uid);
        query.setParameter("authKey", authKey);
        List devices = query.getResultList();
        if (devices != null && devices.size() == 1) {
            return (Device) devices.get(0);
        }
        return null;
    }

}
