package services.db;

import db.ComputerDao;
import db.UserDao;
import entities.Computer;
import entities.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.Crypt;

import java.sql.Timestamp;
import java.util.List;

/**
 * .
 * User: Ostap
 * Date: 2/7/14
 * Time: 9:06 PM
 */
@Service
public class UserServiceImpl implements UserService {

    public static Logger l = Logger.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private ComputerDao computerDao;

    @Override
    @Transactional
    public User registerUser(User user) {

        String encryptedPassword = new StandardPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setAuthKey(Crypt.generateKey());
        user.setDate(new Timestamp(System.currentTimeMillis()));

        user = userDao.create(user);

        return user;
    }


    @Override
    public User getUserForName(String username) {
        User user = userDao.loadUserByUsername(username);
        return user;
    }

    @Override
    public User getUserForAuthKey(String authKey) {
        User user = userDao.loadUserByAuthKey(authKey);
        return user;
    }

    @Override
    public User getUserForEmail(String email) {
        User user = userDao.loadUserByEmail(email);
        return user;
    }

    @Override
    @Transactional
    public boolean connectComputer(String authKey, String connectionKey) {

        Computer computer = computerDao.loadByConnectionKey(connectionKey);
        User user = userDao.loadUserByAuthKey(authKey);
        if (user != null && computer != null) {
            List<User> users = computer.getUsers();

            for (User user1 : users) {
                if (user1.getId() == user.getId()) {
                    return false;
                }
            }

            users.add(user);
            user.getComputers().add(computer);
            userDao.update(user);
            computerDao.update(computer);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void disconnectComputer(String authKey, String connectionKey) {
        User user = userDao.loadUserByAuthKey(authKey);

        Computer toRemove = null;
        for (Computer computer : user.getComputers()) {
            if (computer.getConnectionKey().equals(connectionKey)){
                toRemove = computer;
                break;
            }
        }

        if (toRemove != null) {
            user.getComputers().remove(toRemove);
        }
    }
}
