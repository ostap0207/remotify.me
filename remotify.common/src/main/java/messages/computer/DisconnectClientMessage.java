package messages.computer;

import entities.Computer;
import messages.ClientMessage;
import messages.DefaultClientMessage;

/**
 * Created by User on 27.03.2014.
 */
public class DisconnectClientMessage extends DefaultClientMessage implements ClientMessage {

   public long id = System.currentTimeMillis();
   public String reason;

   public String getReason() {
      return reason;
   }

   public void setReason(String reason) {
      this.reason = reason;
   }

   @Override
   public long getUniqueId() {
      return 0;
   }

   @Override
   public int getMessageId() {
      return DISCONNECT_CLIENT_MESSAGE;
   }
}
