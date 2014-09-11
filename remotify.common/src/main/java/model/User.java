/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Ostap
 */
public class User {
    public int id;
    public String username;
    public String firstname;
    public String lastname;
    public String password;
    public String salt;
    public String email;
    public String authKey;  
    public long registered;
    public long lastConnected;
    public String status;
}
