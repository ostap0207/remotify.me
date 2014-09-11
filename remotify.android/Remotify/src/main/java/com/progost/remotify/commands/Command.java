package com.progost.remotify.commands;

/**
 * Created by Ostap on 5/1/14.
 */
public enum Command {

    VOLUME_UP("volume_up"),
    VOLUME_DOWN("volume_down"),
    MUTE("mute"),
    UNMUTE("unmute"),
    LEFT("left"),
    RIGHT("right"),
    NEXT_WINDOW("next_window"),
    CLOSE_WINDOW("close_window"),
    F5("f5");


   private String command;

   Command(String command){
       this.command = command;
   }


   public String toString(){
       return command;
   }

}
