package db;

import entities.Computer;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

/**
 * .
 * User: Ostap
 * Date: 2/5/14
 * Time: 9:48 PM
 */

@Component
public class ComputerDaoImpl extends GenericDaoImpl<Computer> implements ComputerDao {

    @Override
    public Computer loadByConnectionKey(String connectionKey) {
        Query query = this.em
                .createQuery("select d FROM Computer d where d.connectionKey = :connectionKey");
        query.setParameter("connectionKey", connectionKey);
        List computers = query.getResultList();
        if (computers != null && computers.size() == 1) {
            return (Computer) computers.get(0);
        }
        return null;
    }

    @Override
    public Computer loadByUid(String uid) {
        Query query = this.em
                .createQuery("select d FROM Computer d where d.connectionKey = :uid");
        query.setParameter("uid", uid);
        List computers = query.getResultList();
        if (computers != null && computers.size() == 1) {
            return (Computer) computers.get(0);
        }
        return null;
    }

    @Override
    public Computer loadForUserById(String uid, String authKey) {
        Query query = this.em
                .createQuery("select d FROM Computer d where d.connectionKey = :uid and (select u from User u where u.authKey = :authKey) member of d.users");
        query.setParameter("uid", uid);
        query.setParameter("authKey", authKey);
        List computers = query.getResultList();
        if (computers != null && computers.size() == 1) {
            return (Computer) computers.get(0);
        }
        return null;
    }

}
