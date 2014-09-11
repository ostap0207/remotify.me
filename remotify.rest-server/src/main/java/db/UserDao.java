package db;


import entities.User;

/**
 * Created with IntelliJ IDEA.
 * User: Ostap
 * Date: 2/4/14
 * Time: 12:26 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserDao extends GenericDao<User> {
    /**
     * Returns an User object that matches the username given
     *
     *
     * @param username
     * @return
     */
    public User loadUserByUsername(String username);

    public User loadUserByEmail(String email);

    public User loadUserByAuthKey(String authKey);
}
