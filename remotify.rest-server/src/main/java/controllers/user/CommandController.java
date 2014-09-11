package controllers.user;

import com.remotify.exceptions.commands.SendCommandException;
import com.remotify.exceptions.data.NoSuchComputerException;
import com.remotify.responses.*;
import com.remotify.responses.commands.*;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import messages.device.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.MessageService;

import java.io.UnsupportedEncodingException;

/**
 * Created by User on 28.02.14.
 */

@Controller
@RequestMapping("/user/computer/{uid}")
public class CommandController {

    @Autowired
    private MessageService messageService;

    @RequestMapping("/simplecommand/{type}/{authKey}")
    public @ResponseBody SimpleCommandResponse command(@PathVariable String uid,@PathVariable String type,@PathVariable String authKey) throws Exception {

        SimpleCommandMessage message = new SimpleCommandMessage();
        message.deviceUID = uid;
        message.computerUID = uid;
        message.type = SimpleCommandMessage.Type.valueOf(type.toUpperCase());


        SimpleCommandMessage cm = sendMessage(message);
        if (cm != null){
            return ResponseBuilder.status(SimpleCommandResponse.class, Response.STATUS.OK);
        }else {
            throw new SendCommandException();
        }

    }

    @RequestMapping("/keyboard/{action}/{key}/{authKey}")
    public @ResponseBody KeyboardResponse keyboard(@PathVariable String uid,@PathVariable String action,@PathVariable Integer key ,@PathVariable String authKey) throws Exception {

        KeyboardMessage message = new KeyboardMessage();
        message.deviceUID = uid;
        message.computerUID = uid;
        message.action = action;
        message.key = key;


        KeyboardMessage cm = sendMessage(message);
        if (cm != null){
            return ResponseBuilder.status(KeyboardResponse.class, Response.STATUS.OK);
        }else {
            throw new SendCommandException();
        }
    }

    @RequestMapping("/volume/{value}/{authKey}")
    public @ResponseBody VolumeResponse command(@PathVariable String uid,@PathVariable int value,@PathVariable String authKey) throws Exception {

        VolumeMessage message = new VolumeMessage();
        message.deviceUID = uid;
        message.computerUID = uid;
        message.value = value;

        VolumeMessage cm =  sendMessage(message);
        if (cm != null){
            return ResponseBuilder.status(VolumeResponse.class, Response.STATUS.OK);
        }else {
            throw new SendCommandException();
        }
    }

    @RequestMapping("/fs/{authKey}")
    public @ResponseBody FSCommandResponse filelistForPath(@PathVariable String uid,@RequestParam(required = false) String dirPath,@PathVariable String authKey) throws Exception {
        FileListMessage message = new FileListMessage();
        message.deviceUID = uid;
        message.computerUID = uid;
        message.dirPath = dirPath;

        FileListMessage cm = sendMessage(message);
        if (cm != null){
            FSCommandResponse fsCommandResponse = ResponseBuilder.oneEntity(FSCommandResponse.class, cm.files);
            return fsCommandResponse;
        }else {
            throw new SendCommandException();
        }
    }

    @RequestMapping("/desktop/{authKey}")
         public @ResponseBody ScreenShotResponse desktop(@PathVariable String uid,@PathVariable String authKey) throws Exception {
        ScreenShotMessage message = new ScreenShotMessage();
        return requestImage(ScreenShotResponse.class,message,uid,authKey);
    }

    @RequestMapping("/camera/{authKey}")
    public @ResponseBody CameraResponse camera(@PathVariable String uid,@PathVariable String authKey) throws Exception {
        CameraMessage message = new CameraMessage();
        return requestImage(CameraResponse.class,message,uid,authKey);
    }

    public <T extends OneEntityResponse<String>,M extends ImageMessage> T requestImage(Class<T> clazz,M message,String uid,String authKey) throws Exception {
        message.deviceUID = uid;
        message.computerUID = uid;

        M cm = sendMessage(message);
        if (cm != null){
            T screenShotResponse = ResponseBuilder.oneEntity(clazz, Base64.encode(cm.image));
            return screenShotResponse;
        }else {
            throw new SendCommandException();
        }
    }

    @RequestMapping("/browser/{authKey}")
    public @ResponseBody BrowserResponse browser(@PathVariable String uid,@PathVariable String authKey,@RequestParam String url) throws Exception {
        BrowserMessage message = new BrowserMessage();
        message.deviceUID = uid;
        message.computerUID = uid;
        message.url = url;

        BrowserMessage cm = sendMessage(message);
        if (cm != null){
            BrowserResponse browserResponse = ResponseBuilder.status(BrowserResponse.class, Response.STATUS.OK);
            return browserResponse;
        }else {
            throw new SendCommandException();
        }
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionHandler(UnsupportedEncodingException ex){
        return ResponseBuilder.error(DefaultErrorResponse.class, ErrorResponse.ERROR.SEND_ERROR);
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionHandler(SendCommandException ex){
        return ResponseBuilder.error(DefaultErrorResponse.class, ErrorResponse.ERROR.SEND_ERROR);
    }

    private <T extends CommandMessage> T sendMessage(T message) throws NoSuchComputerException {
        return (T)messageService.sendAndReceive(message.computerUID, message);
    }

}
