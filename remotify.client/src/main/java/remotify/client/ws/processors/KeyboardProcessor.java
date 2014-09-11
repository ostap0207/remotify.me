package remotify.client.ws.processors;

import messages.device.KeyboardMessage;
import remotify.client.profiles.keyboard.Keyboard;

/**
 * Created by Ostap OS on 09.06.2014.
 */
public class KeyboardProcessor implements Processor<KeyboardMessage> {

    @Override
    public KeyboardMessage process(KeyboardMessage message) {
        Keyboard.exec(Keyboard.Action.valueOf(message.action.toUpperCase()),message.key);
        return message;
    }

}
