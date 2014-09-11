/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remotify.client.ws.processors;

import messages.Message;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Ostap
 */
public class ProcessorFactory {
    
    private final static ConcurrentHashMap<Integer,Class> processors;
    
    static{
        processors = new ConcurrentHashMap();
        processors.put(Message.SIMPLE_COMMAND_MESSAGE, CommandProcessor.class);
        processors.put(Message.FILELIST_COMMAND_MESSAGE, FileListProcessor.class);
        processors.put(Message.SCREEN_SHOT_MESSAGE, ScreenShotProcessor.class);
        processors.put(Message.CAMERA_MESSAGE, CameraProcessor.class);
        processors.put(Message.BROWSER_MESSAGE, BrowserProcessor.class);
        processors.put(Message.VOLUME_MESSAGE, VolumeProcessor.class);
        processors.put(Message.KEYBOARD_MESSAGE, KeyboardProcessor.class);
    }
    
    
    public static Processor getProcessor(int id){
        try {
            Class clazz = processors.get(id);
            if (clazz == null)
                return null;
            Processor processor = (Processor)clazz.newInstance();
            return processor;
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        }
        return null;
    }
    
    public static Message process(Message message){
        return getProcessor(message.getMessageId()).process(message);
    }
    
    public static Message processNotNull(Message message) {
        Message resp = process(message);
        if (resp == null)
            return message;
        return resp;
    }
}
