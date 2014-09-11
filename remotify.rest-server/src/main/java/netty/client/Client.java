/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netty.client;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import messages.device.CommandMessage;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.util.CharsetUtil;

/**
 *
 * @author Ostap
 */
public class Client {


    private int port;
    private String host;

    private final ClientHandler handler;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Client() {
        System.out.println("Client started");
        handler = new ClientHandler(this);
    }

    public void sendMessage(final Object request) {

        ChannelFactory cf = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());

        ClientBootstrap bootstrap = new ClientBootstrap(cf);
        
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new ObjectDecoder());
                pipeline.addLast("encoder", new ObjectEncoder());
                pipeline.addLast("handler", handler);
                return pipeline;
            }
        });

        ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));

        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                future.getChannel().write(request);
                System.out.println("message sendend");
            }
        });
    }
    
    public CommandMessage getMessage(){
        return handler.cm;
    }
    
    
}
