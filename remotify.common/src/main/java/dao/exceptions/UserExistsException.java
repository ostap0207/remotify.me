/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.exceptions;

/**
 *
 * @author Ostap
 */
public class UserExistsException extends Exception{

    public UserExistsException() {
        super("User with this email alreadyexists");
    }
    
}
