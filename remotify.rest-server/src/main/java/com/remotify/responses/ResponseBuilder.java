package com.remotify.responses;

import java.util.List;

/**
 * Created by User on 27.02.14.
 */
public class ResponseBuilder<T extends Response,B extends ResponseBuilder> {

    protected Response.STATUS status;
    protected Class<T> clazz;


    public ResponseBuilder(Class<T> clazz){
        this.clazz = clazz;
    }

    public static <R extends OneEntityResponse<D>,D> R oneEntity(Class<R> clazz,D data){
        try {
            R response = clazz.newInstance();
            response.setData(data);
            return response;
        } catch (InstantiationException e) {
            throw new RuntimeException();
        } catch (IllegalAccessException e) {
            throw new RuntimeException();
        }
    }

    public static <R extends ListResponse<D>,D> R list(Class<R> clazz,List<D> list){
        try {
            R response = clazz.newInstance();
            response.setData(list);
            return response;
        } catch (InstantiationException e) {
            throw new RuntimeException();
        } catch (IllegalAccessException e) {
            throw new RuntimeException();
        }
    }

    public static <R extends StatusResponse> R status(Class<R> clazz,Response.STATUS status){
        try {
            R response = clazz.newInstance();
            response.setStatus(status);
            return response;
        } catch (InstantiationException e) {
            throw new RuntimeException();
        } catch (IllegalAccessException e) {
            throw new RuntimeException();
        }
    }

    public static <R extends ErrorResponse> R error(Class<R> clazz,ErrorResponse.ERROR error){
        try {
            R response = clazz.newInstance();
            response.setError(error);
            return response;
        } catch (InstantiationException e) {
            throw new RuntimeException();
        } catch (IllegalAccessException e) {
            throw new RuntimeException();
        }
    }

}
