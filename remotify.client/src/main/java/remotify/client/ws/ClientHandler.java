/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remotify.client.ws;

import messages.ClientMessage;
import messages.Message;
import messages.device.CommandMessage;
import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import remotify.client.Controller;
import remotify.client.ws.processors.ProcessorFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Ostap
 */

@WebSocket(maxTextMessageSize = 2 * 1024 * 1024,maxBinaryMessageSize = 2 * 1024 * 1024)
public class ClientHandler {
    
    private Logger l = Logger.getLogger(ClientHandler.class);
    private ConcurrentHashMap<Long, ClientMessage> responses;
    private Session session;
    
    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        l.info("Disconnected");
        this.session = null;
        Controller.INSTANCE.connectionFinifhed();
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        this.session = session;
        System.out.println("Connected");
    }

    @OnWebSocketMessage
    public void onMessage(Session session, InputStream in) throws Exception {
        ObjectInputStream bis = new ObjectInputStream(in);
        Object obj = bis.readObject();
        if (obj instanceof Message){
            onMessage((Message)obj);
        }
    }
    
    private void onMessage(Message msg){
        l.info("Message received - "  + msg.getClass());
        if (msg instanceof ClientMessage){
            ClientMessage message = (ClientMessage)msg;
            responses.put(message.getUniqueId(), message);
            l.info("Message : (" + message.getMessageId() + "," + message.getUniqueId()+") received");
        }else if (msg instanceof CommandMessage){
            Message request = (Message)msg;
            Message response = ProcessorFactory.processNotNull(request);
            try {
                sendMessage(response);
            } catch (IOException ex) {
                l.error("",ex);
            }
        }
    }
    
    public void sendMessage(Message msg) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        oos = new ObjectOutputStream(byteArrayOutputStream);
        oos.writeObject(msg);
        session.getRemote().sendBytes(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
    }

    
    public ClientHandler() {
        responses = new ConcurrentHashMap();
    }

    public ClientMessage getResponse(long id,long timeout){
        long startTime = System.currentTimeMillis();
        long currentTime = startTime;
        while (startTime + timeout > currentTime) {
            ClientMessage resp = responses.get(id);
            if (resp != null) {
                responses.remove(id);
                return resp;
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
            }
            currentTime = System.currentTimeMillis();
        }
        l.info("Timeout " + timeout + " reached");
        return null;
    }
}
