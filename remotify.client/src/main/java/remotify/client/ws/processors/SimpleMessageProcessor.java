/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remotify.client.ws.processors;

import messages.Message;

/**
 *
 * @author Ostap
 */
public class SimpleMessageProcessor implements Processor{

    @Override
    public Message process(Message message) {
        //do something
        System.out.println("Message processed");
        return message;
    }
    
}
