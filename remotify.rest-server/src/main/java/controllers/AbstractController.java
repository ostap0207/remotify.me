package controllers;

import com.remotify.exceptions.data.ComputerAlreadyConnectedException;
import com.remotify.exceptions.data.NoSuchUserException;
import com.remotify.responses.DefaultErrorResponse;
import com.remotify.responses.ErrorResponse;
import com.remotify.responses.ResponseBuilder;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import services.db.UserService;
import services.security.CustomUserDetails;

import javax.persistence.PersistenceException;
import javax.security.auth.login.LoginException;

/**
 * Created by User on 28.02.14.
 */
public class AbstractController {

    @Autowired
    protected UserService userService;

    public User getUser() throws NoSuchUserException {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserForAuthKey(userDetails.getAuthKey());
        if (user == null){
            throw new NoSuchUserException();
        }
        return user;
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionHandler(PersistenceException ex){
        return ResponseBuilder.error(DefaultErrorResponse.class, ErrorResponse.ERROR.DUPLICATE_ENTRY);
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionHandler(LoginException ex){
        return ResponseBuilder.error(DefaultErrorResponse.class, ErrorResponse.ERROR.WRONG_PASSWORD);
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionHandler(NoSuchUserException ex){
        return ResponseBuilder.error(DefaultErrorResponse.class, ErrorResponse.ERROR.NO_SUCH_COMPUTER);
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionHandler(ComputerAlreadyConnectedException ex){
        return ResponseBuilder.error(DefaultErrorResponse.class, ErrorResponse.ERROR.COMPUTER_ALREADY_CONNECTED);
    }

}
