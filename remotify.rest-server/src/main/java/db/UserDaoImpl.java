package db;

import entities.User;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ostap
 * Date: 2/4/14
 * Time: 12:27 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("userDao")
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

    @Override
         public User loadUserByUsername(String username) {
        Query query = this.em
                .createQuery("select u FROM User u where u.username= :username");
        query.setParameter("username", username);
        List users = query.getResultList();
        if (users != null && users.size() == 1) {
            return (User) users.get(0);
        }
        return null;
    }

    @Override
    public User loadUserByEmail(String email) {
        Query query = this.em
                .createQuery("select u FROM User u where u.email= :email");
        query.setParameter("email", email);
        List users = query.getResultList();
        if (users != null && users.size() == 1) {
            return (User) users.get(0);
        }
        return null;
    }

    @Override
    public User loadUserByAuthKey(String authKey) {
        Query query = this.em
                .createQuery("select u FROM User u where u.authKey= :authKey");
        query.setParameter("authKey", authKey);
        List users = query.getResultList();
        if (users != null && users.size() == 1) {
            return (User) users.get(0);
        }
        return null;
    }
}
