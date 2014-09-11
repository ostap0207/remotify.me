package messages.device;

/**
 * Created by Ostap OS on 09.06.2014.
 */
public class KeyboardMessage extends CommandMessage  {

    public String action;
    public int key;

    @Override
    public int getMessageId() {
        return KEYBOARD_MESSAGE;
    }
}
