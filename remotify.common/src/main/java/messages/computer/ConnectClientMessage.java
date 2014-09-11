/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messages.computer;

import entities.Computer;
import messages.ClientMessage;
import messages.DefaultClientMessage;

/**
 *
 * @author Ostap
 */
public class ConnectClientMessage extends DefaultClientMessage implements ClientMessage {

    public long id = System.currentTimeMillis();

    //out
    public String status;
    
    @Override
    public long getUniqueId() {
        return id;
    }


   @Override
    public int getMessageId() {
        return CONNECT_CLIENT_MESSAGE;
    }
    
}
