package ws;

import messages.Message;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Ostap
 * Date: 2/1/14
 * Time: 12:36 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class MessageHandler extends BinaryWebSocketHandler {

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        super.handleBinaryMessage(session, message);
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(message.getPayload().array()));
        Object obj = objectInputStream.readObject();
        if (obj instanceof Message){
            handleMessage(session,(Message)obj);
        }
    }

    protected abstract void handleMessage(WebSocketSession session, Message msg);
}
