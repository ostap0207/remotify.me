package services.db;

import entities.Computer;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

/**
 * .
 * User: Ostap
 * Date: 2/7/14
 * Time: 10:28 PM
 */
public interface ComputerService {

    public Computer create(Computer computer);

    public Computer getByConnectionKey(String connectionKey);

    public Computer connectComputer(Computer computer, WebSocketSession session);

    public void disconnectComputerForSession(WebSocketSession session);

    public void disconnectComputerForSession(Computer computer);

    public void disconnectComputerForSession(String computerUid);

    public List<Computer> getComputersForUser(String authKey);

    public Computer getComputerForUser(String uid,String authKey);

}
