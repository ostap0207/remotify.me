package controllers.user;

import com.remotify.exceptions.data.ComputerAlreadyConnectedException;
import com.remotify.exceptions.data.NoSuchComputerException;
import com.remotify.responses.Response;
import com.remotify.responses.ResponseBuilder;
import com.remotify.responses.StatusResponse;
import com.remotify.responses.data.ComputerResponse;
import com.remotify.responses.data.ComputersResponse;
import controllers.AbstractController;
import entities.Computer;
import http.requests.ComputerConnectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.db.ComputerService;
import services.db.UserService;

import java.util.List;

/**
 * Created by User on 28.02.14.
 */

@Controller
@RequestMapping("/user")
public class ComputerController extends AbstractController {

    @Autowired
    private ComputerService computerService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/computer/{authKey}",method= RequestMethod.POST)
    public @ResponseBody
    StatusResponse addComputer(@RequestBody ComputerConnectRequest computerConnectRequest,@PathVariable String authKey) throws ComputerAlreadyConnectedException {
        boolean connected = userService.connectComputer(authKey,computerConnectRequest.getConnectionKey());
        if (connected)
            return ResponseBuilder.status(StatusResponse.class, Response.STATUS.OK);
        throw new ComputerAlreadyConnectedException();
    }

    @RequestMapping(value = "/computer/{uid}/{authKey}",method= RequestMethod.GET)
    public @ResponseBody ComputerResponse addComputer(@PathVariable String uid,@PathVariable String authKey) throws Exception{
        Computer computer = computerService.getComputerForUser(uid,authKey);
        if (computer != null)
            return ResponseBuilder.oneEntity(ComputerResponse.class,computer);
        throw new NoSuchComputerException();
    }

    @RequestMapping(value = "/computers/{authKey}",method= RequestMethod.GET)
    public @ResponseBody
    ComputersResponse getComputers(@PathVariable String authKey) {
        List<Computer> computers = computerService.getComputersForUser(authKey);
        ComputersResponse response = ResponseBuilder.list(ComputersResponse.class, computers);
        return response;
    }

    @RequestMapping(value = "/computer/{uid}/{authKey}",method= RequestMethod.DELETE)
    public @ResponseBody
    StatusResponse deleteComputer(@PathVariable String uid,@PathVariable String authKey) {
        userService.disconnectComputer(authKey,uid);
        return ResponseBuilder.status(StatusResponse.class, Response.STATUS.OK);
    }

}
