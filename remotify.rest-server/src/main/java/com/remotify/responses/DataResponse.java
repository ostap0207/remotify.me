package com.remotify.responses;

/**
 * Created by User on 27.02.14.
 */
public interface DataResponse<T> {

    public void setData(T data);

    public T getData();

}
