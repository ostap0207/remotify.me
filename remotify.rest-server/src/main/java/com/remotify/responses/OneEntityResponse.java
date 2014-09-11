package com.remotify.responses;

/**
 * Created by User on 27.02.14.
 */
public class OneEntityResponse<T> extends StatusResponse implements DataResponse<T>  {

    public OneEntityResponse(){
        setStatus(STATUS.OK);
    }

    protected T data;

    @Override
    public void setData(T data) {
        this.data = data;
    }

    @Override
    public T getData() {
        return data;
    }

}
