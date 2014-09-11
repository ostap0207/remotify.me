package ws.processors;

import messages.computer.DisconnectClientMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.db.ComputerService;
import ws.Context;

/**
 * Created by User on 27.03.2014.
 */
@Service
public class DisconnectClientProcessor implements Processor<DisconnectClientMessage> {


   @Autowired
   private ComputerService computerService;


   @Override
   public DisconnectClientMessage process(Context context, DisconnectClientMessage message) {
      computerService.disconnectComputerForSession(message.getComputer().getConnectionKey());
      return message;
   }
}

