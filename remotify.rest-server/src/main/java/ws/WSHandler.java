package ws;

import entities.Computer;
import messages.ClientMessage;
import messages.Message;
import messages.computer.ConnectClientMessage;
import messages.device.CommandMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import services.MessageService;
import services.db.ComputerService;
import ws.processors.ProcessorFactory;

import java.io.IOException;

/**
 * User: Ostap
 * Date: 2/1/14
 * Time: 12:36 AM
 */

public class WSHandler extends MessageHandler {

    private Logger l = Logger.getLogger(WSHandler.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private ComputerService computerService;

    @Autowired
    private ProcessorFactory processorFactory;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        l.info("Computer connected");
    }

    @Override
    protected void handleMessage(WebSocketSession session, Message msg) {
        l.info("Message received");
            if (msg instanceof CommandMessage) {
                CommandMessage cm = (CommandMessage) msg;
                messageService.handleMessage(session, cm);
                return;
            }

            if (msg instanceof ClientMessage){
                if (msg instanceof ConnectClientMessage){
                    ConnectClientMessage cm = (ConnectClientMessage) msg;
                    Computer computer = computerService.connectComputer(cm.getComputer(),session);
                    cm.status = "SUCCESS";
                    cm.getComputer().setConnectionKey(computer.getConnectionKey());
                    try {
                        messageService.sendMessage(session, cm);
                    } catch (IOException ex) {
                        l.error("Error while sending", ex);
                    }
                }else{
                    Message resp = processorFactory.getProcessor(msg.getMessageId()).process(null,msg);
                    try {
                        messageService.sendMessage(session, resp);
                    } catch (IOException ex) {
                        l.error("Error while sending", ex);
                    }
                }
            }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        l.info("Computer disconnected");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        l.info("Computer disconnected2");
        exception.printStackTrace();
    }
}
