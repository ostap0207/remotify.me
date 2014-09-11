package messages.device;

/**
 * .
 * User: Ostap
 * Date: 5/18/14
 * Time: 10:15 PM
 */
public class VolumeMessage extends CommandMessage {

    public int value;

    @Override
    public int getMessageId() {
        return VOLUME_MESSAGE;
    }
}
