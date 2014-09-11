/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messages.device;


/**
 *
 * @author Ostap
 */
public class SimpleCommandMessage extends CommandMessage{

    //in
    public Type type;
    
    //out
    public String status;
    
    public enum Type{
        RIGHT, LEFT, UP, DOWN , SPACE, FULL_SCREEN , ENTER,
        VOLUME_UP, VOLUME_DOWN, PAUSE, PLAY, STOP, MUTE, UNMUTE, SCREEN_OFF,
        REBOOT, SWITCH_OFF, F5, NEXT_WINDOW, CLOSE_WINDOW
    }
    
    @Override
    public int getMessageId() {
        return SIMPLE_COMMAND_MESSAGE;
    }
    
}
