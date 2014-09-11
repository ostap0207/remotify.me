/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netty.client;

import messages.device.CommandMessage;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 *
 * @author Ostap
 */
public class ClientHandler extends SimpleChannelHandler{

    private final Client client;
    protected CommandMessage cm;

    public ClientHandler(Client client) {
        this.client = client;
    }
    
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println("Message received");
        synchronized (client) {
            cm = (CommandMessage) e.getMessage();
            client.notify();
        }
    }
    
}
