package ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * Created with IntelliJ IDEA.
 * User: Ostap
 * Date: 2/1/14
 * Time: 1:06 AM
 * To change this template use File | Settings | File Templates.
 */

@Configuration
@EnableWebSocket
public class WSConfigutration implements WebSocketConfigurer{

    @Value("${wsBufferSize}")
    private int wsBufferSize;

    @Autowired
    private WSHandler wsHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(wsHandler,"/ws");
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(wsBufferSize);
        container.setMaxBinaryMessageBufferSize(wsBufferSize);
        return container;
    }
}
