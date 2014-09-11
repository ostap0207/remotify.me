package remotify.client.ws.processors;

import messages.device.CameraMessage;
import remotify.client.profiles.camera.Camera;

import java.awt.image.BufferedImage;

/**
 * .
 * User: Ostap
 * Date: 5/8/14
 * Time: 12:07 AM
 */
public class CameraProcessor extends ImageProcessor<CameraMessage> {
    @Override
    protected BufferedImage getImage() {
        return Camera.takePicture();
    }
}
