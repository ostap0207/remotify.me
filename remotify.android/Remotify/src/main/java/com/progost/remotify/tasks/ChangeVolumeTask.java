package com.progost.remotify.tasks;

import com.progost.remotify.GeneralComputerActivity;

/**
 * Created by Ostap on 5/18/2014.
 */
public class ChangeVolumeTask extends ClientAsyncTask<Integer,Void,String>  {


    public ChangeVolumeTask(GeneralComputerActivity activity) {
        super(activity);
    }

    @Override
    protected String execInBackground(Integer... integers) {
        try {
            return client.changeVolume(key,mac,integers[0]);
        } catch (Exception e) {
            return null;
        }
    }


}
