/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remotify.client.ws.processors;

import messages.device.SimpleCommandMessage;
import remotify.client.Controller;
import remotify.client.profiles.keyboard.Keyboard;
import utils.platformdepended.Platform;

/**
 *
 * @author Ostap
 */
public class CommandProcessor implements Processor<SimpleCommandMessage>{

    @Override
    public SimpleCommandMessage process(SimpleCommandMessage message) {
       if (message instanceof SimpleCommandMessage){
           SimpleCommandMessage scm = (SimpleCommandMessage)message;
           System.out.println(scm.type);
           switch(scm.type){
               case RIGHT:
                   Keyboard.right();
                   break;
               case LEFT:
                   Keyboard.left();
                   break;
               case UP:
                   Keyboard.up();
                   break;
               case DOWN:
                   Keyboard.down();
                   break;
               case SPACE:
                   Keyboard.space();
                   break;
               case ENTER:
                   Keyboard.enter();
                   break;
               case VOLUME_DOWN:
                   Controller.INSTANCE.getAudio().volumeDown();
                   break;
               case VOLUME_UP:
                   Controller.INSTANCE.getAudio().volumeUp();
                   break;
               case MUTE:
                   Controller.INSTANCE.getAudio().mute();
                   break;
               case UNMUTE:
                   Controller.INSTANCE.getAudio().unmute();
                   break;
               case F5:
                   Keyboard.f5();
                   break;
               case NEXT_WINDOW:
                   Keyboard.nextWindow();
                   break;
               case CLOSE_WINDOW:
                   Keyboard.closeWindow();
                   break;
               case SWITCH_OFF:
                   Platform.OS.shutdown();
                   break;
           }
       }
        return null;
    }
    
}
