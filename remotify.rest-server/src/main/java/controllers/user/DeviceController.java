package controllers.user;

import com.remotify.responses.ResponseBuilder;
import com.remotify.responses.data.DeviceResponse;
import controllers.AbstractController;
import entities.Device;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.db.DeviceService;
import services.db.UserService;

/**
 * Created by User on 28.02.14.
 */

@Controller
@RequestMapping("/user")
public class DeviceController extends AbstractController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceService deviceService;


    @ResponseBody
    @RequestMapping(value = "/device/{authKey}",method= RequestMethod.POST)
    public DeviceResponse addDevice(@PathVariable String id,@PathVariable String authKey, @RequestBody Device device){
        User user = userService.getUserForAuthKey(authKey);
        device = deviceService.createForUser(device,user);
        DeviceResponse response = ResponseBuilder.oneEntity(DeviceResponse.class, device);
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "/device/{uid}/{authKey}",method= RequestMethod.GET)
    public DeviceResponse getDevice(@PathVariable String uid,@PathVariable String authKey){
        Device device = deviceService.getForUidAndUser(uid, authKey);
        DeviceResponse response = ResponseBuilder.oneEntity(DeviceResponse.class, device);
        return response;
    }

}
