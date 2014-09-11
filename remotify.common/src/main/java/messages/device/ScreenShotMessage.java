/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messages.device;

import messages.Message;

/**
 *
 * @author Ostap
 */
public class ScreenShotMessage extends ImageMessage{


    @Override
    public int getMessageId() {
        return SCREEN_SHOT_MESSAGE;
    }
    
}
