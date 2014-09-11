package com.remotify.responses;

import com.remotify.responses.OneEntityResponse;

import java.util.List;

/**
 * Created by User on 27.02.14.
 */
public class ListResponse<T> extends OneEntityResponse<List<T>> {

    public void add(T elem){
        data.add(elem);
    }

}
