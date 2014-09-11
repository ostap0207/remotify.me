package messages.device;

import messages.Message;

/**
 * .
 * User: Ostap
 * Date: 5/7/14
 * Time: 11:49 PM
 */
public class CameraMessage extends ImageMessage {
    @Override
    public int getMessageId() {
        return Message.CAMERA_MESSAGE;
    }
}
