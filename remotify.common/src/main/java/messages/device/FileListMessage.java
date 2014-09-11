/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messages.device;

import entities.File;

/**
 *
 * @author Ostap
 */
public class FileListMessage extends CommandMessage{

    //in
    public String dirPath;
    
    //out
    public File[] files;
    
    @Override
    public int getMessageId() {
      return FILELIST_COMMAND_MESSAGE;
    }
    
}
