package com.remotify.responses;

import com.remotify.responses.ErrorResponse;

/**
 * Created by User on 27.02.14.
 */
public class DefaultErrorResponse extends ErrorResponse {

    protected ERROR error;

    @Override
    public void setError(ERROR error) {
        this.error = error;
    }

    @Override
    public ERROR getError() {
        return error;
    }
}
