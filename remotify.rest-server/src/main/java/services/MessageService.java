package services;

import com.remotify.exceptions.data.NoSuchComputerException;
import db.HistoryDao;
import db.SessionDao;
import entities.History;
import messages.Message;
import messages.device.CommandMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import ws.SessionRegistry;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Ostap
 * Date: 2/1/14
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */

@Service
public class MessageService {

    private Map<String, CommandMessage> messages = new ConcurrentHashMap<String, CommandMessage>();
    private Map<String, Object> waiters = new ConcurrentHashMap<String, Object>();

    @Autowired
    private SessionRegistry registry;

    @Autowired
    private SessionDao sessionDao;

    @Autowired
    private HistoryDao historyDao;

    public SessionRegistry getRegistry() {
        return registry;
    }

    public void handleMessage(WebSocketSession session, CommandMessage msg){
        Object monitor = waiters.remove(session.getId());
        if (monitor == null)
            return;

        synchronized (monitor){
            messages.put(session.getId(),msg);
            monitor.notify();
        }
    }

    public void sendMessage(String id, Message msg) throws IOException {
        sendMessage(registry.get(id),msg);
    }

    public void sendMessage(WebSocketSession session, Message msg) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
        oos.writeObject(msg);

        BinaryMessage bm = new BinaryMessage(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
        if (session != null){
            session.sendMessage(bm);
        }
    }

    @Transactional
    public Message sendAndReceive(String id,CommandMessage msg) throws NoSuchComputerException {
        return sendAndReceive(id,msg,0);
    }

    @Transactional
    public Message sendAndReceive(String id,CommandMessage msg, int timeout) throws NoSuchComputerException {
        Object monitor = new Object();
        WebSocketSession session = registry.get(id);
        if (session == null)
            throw new NoSuchComputerException();

        History history = new History();
        history.setStatus("SENT");
        history.setMessage(msg.getClass().getName());
        history.setSession(sessionDao.loadLatestForComputerByUid(id));
        history.setType("to computer");
        history = historyDao.create(history);

        synchronized (monitor){
            try {
                waiters.put(session.getId(), monitor);
                sendMessage(session, msg);


                monitor.wait(timeout);
            } catch (InterruptedException e) {
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                waiters.remove(monitor);

            }
        }
        return messages.remove(session.getId());
    }

}
