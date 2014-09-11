/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messages.device;

import messages.Message;

/**
 *
 * @author Ostap
 */
public class ConnectToComputerMessage implements Message{

    public String connectionKey;
    
    
    @Override
    public int getMessageId() {
        return CONNECT_TO_COMPUTER_MESSAGE;
    }
    
}
