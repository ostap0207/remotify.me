package com.remotify.responses;

/**
 * Created by User on 27.02.14.
 */
public class StatusResponse implements Response {

    protected STATUS status;

    @Override
    public STATUS getStatus() {
        return status;
    }

    @Override
    public void setStatus(STATUS status) {
        this.status = status;
    }
}
