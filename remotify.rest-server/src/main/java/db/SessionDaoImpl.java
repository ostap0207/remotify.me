package db;

import entities.Session;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

/**
 * .
 * User: Ostap
 * Date: 5/25/14
 * Time: 8:32 PM
 */

@Component("sessionDao")
public class SessionDaoImpl extends GenericDaoImpl<Session> implements SessionDao {
    @Override
    public Session loadByUid(String uid) {
        Query query = this.em
                .createQuery("select s FROM Session s where s.uid = :uid");
        query.setParameter("uid", uid);
        List sessions = query.getResultList();
        if (sessions != null && sessions.size() == 1) {
            return (Session) sessions.get(0);
        }
        return null;
    }

    @Override
    public Session loadLatestForComputer(int computerId) {
        Query query = this.em
                .createQuery("select s FROM Session s where s.computer.id = :computerId ORDER BY s.id desc")
                .setMaxResults(1);
        query.setParameter("computerId", computerId);
        List sessions = query.getResultList();
        if (sessions.size() > 0){
            return (Session)sessions.get(0);
        }
        return null;
    }

    @Override
    public Session loadLatestForComputerByUid(String uid) {
        Query query = this.em
                .createQuery("select s FROM Session s where s.computer.connectionKey = :uid ORDER BY s.id desc")
                .setMaxResults(1);
        query.setParameter("uid", uid);
        List sessions = query.getResultList();
        if (sessions.size() > 0){
            return (Session)sessions.get(0);
        }
        return null;
    }
}
