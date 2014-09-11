/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remotify.client.ws;

import entities.Computer;
import messages.ClientMessage;
import messages.computer.ConnectClientMessage;
import messages.computer.DisconnectClientMessage;
import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Ostap
 */
public class Client {

   private static final String SUCCESS = "SUCCESS";
   private Logger l = Logger.getLogger(Client.class);
    
    private String host;
    private boolean connected = false;
    private static ClientHandler socket;
    private Computer computer;
    private static WebSocketClient wsclient;

    static{
        wsclient = new WebSocketClient();
        wsclient.setMaxBinaryMessageBufferSize(2 * 1024 * 1024);
        wsclient.setMaxTextMessageBufferSize(2 * 1024 * 1024);
        socket = new ClientHandler();
    }

    public Client(String host, Computer computer) {
        this.host = host;
        this.computer = computer;
    }

    public boolean start() {
        try {
            wsclient.start();
            URI echoUri = new URI(host);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            Future<Session> future = wsclient.connect(socket, echoUri, request);
            future.get(5, TimeUnit.SECONDS);
            System.out.printf("Connecting to : %s%n", echoUri);
        } catch (Throwable t) {
            try {
                wsclient.stop();
            } catch (Exception e) {
            }
            l.error("Client can not connect to remote server",t);
            return false;
        }
        return true;
    }

    public void stop(){
        try {
            wsclient.stop();
        } catch (Exception e) {
        }
    }

   public ConnectClientMessage connect() throws IOException {
      ConnectClientMessage message = new ConnectClientMessage();
      ConnectClientMessage resp = (ConnectClientMessage) sendAndReceive(message,5000);
      if (resp != null && resp.status.equals(SUCCESS)){
         computer = resp.getComputer();
      }
      return resp;
   }

   public DisconnectClientMessage disconnect() throws IOException {
      DisconnectClientMessage message = new DisconnectClientMessage();
      DisconnectClientMessage resp = ((DisconnectClientMessage) sendAndReceive(message,5000));
      return resp;
   }

    public ClientMessage sendAndReceive(ClientMessage msg,long timeout) throws IOException {
        msg.setComputer(computer);
        socket.sendMessage(msg);
        l.info("Message sended");
        l.info("Waiting for response");

        ClientMessage resp = socket.getResponse(msg.getUniqueId(),timeout);
        l.info("Response is received");
        return resp;
    }
    
}
