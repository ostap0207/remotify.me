/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remotify.client.profiles.screen;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author Ostap
 */
public class Screen {
    
    public static BufferedImage shot() throws AWTException{
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage capture = new Robot().createScreenCapture(screenRect);
        return capture;
    }
    
}
