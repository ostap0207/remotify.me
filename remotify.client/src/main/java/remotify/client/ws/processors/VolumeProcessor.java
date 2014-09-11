package remotify.client.ws.processors;

import messages.device.VolumeMessage;
import remotify.client.Controller;

/**
 * .
 * User: Ostap
 * Date: 5/18/14
 * Time: 10:23 PM
 */
public class VolumeProcessor implements Processor<VolumeMessage> {
    @Override
    public VolumeMessage process(VolumeMessage message) {
        Controller.INSTANCE.getAudio().changeVolume(message.value);
        return message;
    }
}
