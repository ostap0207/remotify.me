package services.db;

import entities.User;

/**
 * .
 * User: Ostap
 * Date: 2/7/14
 * Time: 9:00 PM
 */


public interface UserService {

    public User registerUser(User user);

    public User getUserForName(String username);

    public User getUserForAuthKey(String authKey);

    public User getUserForEmail(String email);

    public boolean connectComputer(String authKey, String connectionKey);

    public void disconnectComputer(String authKey, String connectionKey);
}
