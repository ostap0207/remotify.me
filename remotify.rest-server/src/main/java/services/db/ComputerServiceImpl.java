package services.db;

import db.ComputerDao;
import db.SessionDao;
import db.UserDao;
import entities.Computer;
import entities.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;
import services.MessageService;
import utils.Crypt;

import java.util.List;

/**
 * .
 * User: Ostap
 * Date: 2/7/14
 * Time: 10:29 PM
 */

@Service
public class ComputerServiceImpl implements ComputerService {


    @Autowired
    private MessageService messageService;

    @Autowired
    private ComputerDao computerDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SessionDao sessionDao;

    @Override
    @Transactional
    public Computer create(Computer computer) {
        return computerDao.create(computer);
    }

    @Override
    @Transactional(readOnly = true)
    public Computer getByConnectionKey(String connectionKey) {
        return computerDao.loadByConnectionKey(connectionKey);
    }

    @Override
    @Transactional
    public Computer connectComputer(Computer computer, WebSocketSession session) {
        String key = Crypt.generateKey();
        Computer newComp = computerDao.loadByUid(computer.getConnectionKey());
        boolean exists = true;
        if (newComp == null){
            newComp = computer;
            exists = false;
        }
        newComp.setStatus("online");

        if (!exists){
            newComp.setConnectionKey(key);
            newComp = computerDao.create(newComp);
        }

        Session s = new Session();
        s.setComputer(newComp);
        s.setStatus("GOING");
        s.setUid(session.getId());
        sessionDao.create(s);

        messageService.getRegistry().register(newComp.getConnectionKey(),session);
        return newComp;
    }

    @Override
    @Transactional
    public void disconnectComputerForSession(WebSocketSession session) {
        String uid = messageService.getRegistry().getComputerUidForSession(session.getId());
        if (uid != null){
            Computer computer = computerDao.loadByUid(uid);
            computer.setStatus("offline");
            computerDao.update(computer);

            Session s = sessionDao.loadLatestForComputer(computer.getId());
            if (s != null){
                s.setStatus("ENDED");
            }
            messageService.getRegistry().removeSession(session.getId());
        }
    }

    @Override
    @Transactional
    public void disconnectComputerForSession(Computer computer) {
       disconnectComputerForSession(computer.getConnectionKey());
    }

   @Override
   @Transactional
   public void disconnectComputerForSession(String computerUid) {
      WebSocketSession session = messageService.getRegistry().getSessionForComputer(computerUid);
      disconnectComputerForSession(session);
   }


    @Override
    @Transactional(readOnly = true)
    public List<Computer> getComputersForUser(String authKey) {
        List<Computer> computers = userDao.loadUserByAuthKey(authKey).getComputers();
        computers.size();
        return computers;
    }

    @Override
    @Transactional(readOnly = true)
    public Computer getComputerForUser(String uid,String authKey) {
        Computer computer = computerDao.loadForUserById(uid,authKey);
        return computer;
    }
}
