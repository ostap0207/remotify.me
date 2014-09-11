/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import entities.Computer;

/**
 *
 * @author Ostap
 * 
 * Message which is used to communication between desktop and server.
 */

public interface ClientMessage extends Message{
    
    /**
     * 
     * @return unique id for every object
     * For example it could be current timestamp
     */
    public long getUniqueId();


    public Computer getComputer();


    public void setComputer(Computer computer);
}
