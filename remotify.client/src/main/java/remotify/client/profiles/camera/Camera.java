package remotify.client.profiles.camera;

import com.github.sarxos.webcam.Webcam;

import java.awt.image.BufferedImage;

/**
 * .
 * User: Ostap
 * Date: 5/8/14
 * Time: 12:08 AM
 */
public class Camera {

    public static BufferedImage takePicture(){
        Webcam webcam = Webcam.getDefault();
        webcam.open();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        BufferedImage capture = webcam.getImage();
        webcam.close();
        return capture;
    }

}
