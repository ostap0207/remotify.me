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
public interface Processor<T extends Message> {
    
    public T process(T message);
    
}
