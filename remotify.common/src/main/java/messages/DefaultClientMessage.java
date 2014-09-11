package messages;

import entities.Computer;

/**
 * Created by User on 27.03.2014.
 */
public abstract class DefaultClientMessage implements ClientMessage {

   protected Computer computer;

   @Override
   public void setComputer(Computer computer) {
      this.computer = computer;
   }

   @Override
   public Computer getComputer() {
      return computer;
   }
}
