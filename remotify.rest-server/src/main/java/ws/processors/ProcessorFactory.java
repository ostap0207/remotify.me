/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.processors;

import messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 *
 * @author Ostap
 */

@Service
public class ProcessorFactory {

    private static HashMap<Integer,Processor> processors = new HashMap<Integer,Processor>();

    private static ConnectClientProcessor connectClientProcessor;
    private static DisconnectClientProcessor disconnectClientProcessor;

    @Autowired
    public void setConnectClientProcessor(ConnectClientProcessor connectClientProcessor) {
        ProcessorFactory.connectClientProcessor = connectClientProcessor;
        processors.put(Message.CONNECT_CLIENT_MESSAGE, connectClientProcessor);
    }

   @Autowired
   public void setDisconnectClientProcessor(DisconnectClientProcessor disconnectClientProcessor) {
      ProcessorFactory.disconnectClientProcessor = disconnectClientProcessor;
      processors.put(Message.DISCONNECT_CLIENT_MESSAGE, disconnectClientProcessor);
   }

    public Processor getProcessor(int id){
        return processors.get(id);
    }

}
