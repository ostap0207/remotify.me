package controllers.user;

import com.remotify.responses.Response;
import com.remotify.responses.ResponseBuilder;
import com.remotify.responses.auth.LogoutResponse;
import com.remotify.responses.auth.RegistrationResponse;
import com.remotify.responses.data.UserResponse;
import controllers.AbstractController;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import services.db.UserService;
import services.security.CustomUserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by User on 28.02.14.
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(method= RequestMethod.GET)
    public UserResponse login() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserForAuthKey(userDetails.getAuthKey());
        user.setPassword("");
        UserResponse response = ResponseBuilder.oneEntity(UserResponse.class, user);
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "/logout",method= RequestMethod.GET)
    public LogoutResponse logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextLogoutHandler ctxLogOut = new SecurityContextLogoutHandler();
        ctxLogOut.logout(request, response, auth); // concern you
        return ResponseBuilder.status(LogoutResponse.class, Response.STATUS.OK);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public RegistrationResponse add(@RequestBody User user){
        user = userService.registerUser(user);
        RegistrationResponse response = ResponseBuilder.oneEntity(RegistrationResponse.class, user);
        return response;
    }
}