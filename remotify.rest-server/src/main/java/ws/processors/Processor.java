/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.processors;

import messages.Message;
import ws.Context;

/**
 *
 * @author Ostap
 */
public interface Processor<T extends Message> {

    public T process(Context context, T message);
    
}
