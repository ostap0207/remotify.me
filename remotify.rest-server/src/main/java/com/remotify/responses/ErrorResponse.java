package com.remotify.responses;

/**
 * Created by User on 27.02.14.
 */
public abstract class ErrorResponse extends StatusResponse {

    public enum ERROR{
        DUPLICATE_ENTRY,
        WRONG_PASSWORD,
        SEND_ERROR,
        WRONG_USER_AUTH_KEY,
        NO_SUCH_COMPUTER,
        COMPUTER_ALREADY_CONNECTED
    }

    public ErrorResponse(){
        status = STATUS.ERROR;
    }

    @Override
    public void setStatus(STATUS status) {
        throw new IllegalArgumentException();
    }

    public abstract void setError(ERROR error);

    public abstract ERROR getError();

}
