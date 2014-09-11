/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remotify.client.ws.processors;

import messages.device.FileListMessage;
import org.apache.log4j.Logger;
import utils.OS;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 *
 * @author Ostap
 */
public class FileListProcessor implements Processor<FileListMessage>{

    Logger l = Logger.getLogger(FileListProcessor.class);

    @Override
    public FileListMessage process(FileListMessage message) {
        entities.File[] files;
        if (message.dirPath == null || message.dirPath.isEmpty()){
            File[] drives = File.listRoots();
            files = filterDrives(drives);
        }else{
            String dirPath = message.dirPath;
            try {
                dirPath = URLDecoder.decode(dirPath,"UTF-8");
            } catch (UnsupportedEncodingException ex) {
                l.error("",ex);
            }

            File file = new File(dirPath);
            if (file.isDirectory()){
                files = filterFiles(file.listFiles());
            }else{
                try {
                    OS.openFile(file);
                } catch (IOException ex) {
                    l.error("Can not open file", ex);
                }
                files = filterFiles(file.getParentFile().listFiles());
            }
        }
        
        message.files = files;
        return message;
    }
    
    private entities.File[] filterFiles(File[] files){
        entities.File[] result =  new entities.File[files.length];
        for (int i = 0; i < files.length; i++) {
            result[i] = new entities.File();
            try{
                result[i].path = URLEncoder.encode(new String(files[i].getAbsolutePath()),"UTF-8");
                result[i].name = URLEncoder.encode(new String(files[i].getName()),"UTF-8");
            }catch (Exception e){
            }
        }
        return result;
    }
    
    private entities.File[] filterDrives(File[] files) {
        entities.File[] result = new entities.File[files.length];
        for (int i = 0; i < files.length; i++) {
            result[i] = new entities.File();
            result[i].path = files[i].getAbsolutePath();
            result[i].name = files[i].getAbsolutePath();
        }
        return result;
    }
    
}
