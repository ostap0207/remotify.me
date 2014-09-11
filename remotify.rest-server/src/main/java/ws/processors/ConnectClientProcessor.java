/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.processors;

import db.ComputerDao;
import entities.Computer;
import messages.computer.ConnectClientMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.MessageService;
import services.db.ComputerService;
import utils.Crypt;
import ws.Context;

import javax.persistence.PersistenceException;


/**
 *
 * @author Ostap
 */

@Service
public class ConnectClientProcessor implements Processor<ConnectClientMessage> {


    @Autowired
    private ComputerService computerService;

    @Autowired
    private ComputerDao computerDao;

    @Override
    public ConnectClientMessage process(Context context, ConnectClientMessage message) {
        return message;
    }
}
