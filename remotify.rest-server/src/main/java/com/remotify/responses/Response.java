package com.remotify.responses;

/**
 * Created by User on 27.02.14.
 */
public interface Response {

    public enum STATUS{
        OK, ERROR
    }

    public STATUS getStatus();

    public void setStatus(STATUS status);

}
