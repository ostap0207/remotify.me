/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import java.io.Serializable;

/**
 *
 * @author Ostap
 * Message which is used to communication
 */
public interface Message extends Serializable{
    
    public static final int CONNECT_CLIENT_MESSAGE = 1;
    public static final int DISCONNECT_CLIENT_MESSAGE = 2;
    public static final int CONNECT_TO_COMPUTER_MESSAGE = 3;

    public static final int SIMPLE_COMMAND_MESSAGE = 4;
    public static final int FILELIST_COMMAND_MESSAGE = 5;
    public static final int SCREEN_SHOT_MESSAGE = 6;
    public static final int CAMERA_MESSAGE = 7;
    public static final int BROWSER_MESSAGE = 8;
    public static final int VOLUME_MESSAGE = 9;
    public static final int KEYBOARD_MESSAGE = 10;

    /**
     * 
     * @return must return unique message id for every type of messages
     */
    public int getMessageId();

}
