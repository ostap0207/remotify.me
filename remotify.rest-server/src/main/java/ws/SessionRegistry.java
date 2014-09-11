package ws;

import db.ComputerDao;
import entities.Computer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;
import org.springframework.web.socket.WebSocketSession;
import services.db.ComputerService;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionRegistry {

    @Autowired
    ComputerService computerService;

    private Logger l = Logger.getLogger(SessionRegistry.class);
    private Map<String, WebSocketSession> channels = new ConcurrentHashMap();
    private Map<String, String> clients = new ConcurrentHashMap();

    public synchronized void register(String id, WebSocketSession channel){
        channels.put(id, channel);
        clients.put(channel.getId(),id);
        l.info("Client : " + id + " is registered");
        l.info("Clients amount : " + channels.size());
    }

    public synchronized void removeSession(String id){
        String clientUid = clients.get(id);
        if (clientUid != null){
            clients.remove(id);
            channels.remove(clientUid);
            l.info("Client : " + id +" is unregistered");
            l.info("Clients amount : " + channels.size());
        }else{
            l.warn("No client with id : " + id + "");
        }
    }

    public String getComputerUidForSession(String sessionId){
        return clients.get(sessionId);
    }

    public WebSocketSession getSessionForComputer(String computerId){
        return channels.get(computerId);
    }

    public WebSocketSession get(String id){
        return channels.get(id);
    }

}