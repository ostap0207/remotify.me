/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remotify.client.ws.processors;

import messages.device.ScreenShotMessage;
import remotify.client.profiles.screen.Screen;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author Ostap
 */
public class ScreenShotProcessor extends ImageProcessor<ScreenShotMessage>{

    @Override
    protected BufferedImage getImage() throws AWTException {
        return Screen.shot();
    }
}
