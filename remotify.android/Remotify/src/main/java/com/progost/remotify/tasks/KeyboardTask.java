package com.progost.remotify.tasks;

import com.progost.remotify.GeneralComputerActivity;

/**
 * Created by Ostap OS on 09.06.2014.
 */
public class KeyboardTask extends ClientAsyncTask<String,Void,String> {

    public KeyboardTask(GeneralComputerActivity activity) {
        super(activity);
    }

    @Override
    protected String execInBackground(String... strings) {
        try {
            return client.keyboard(key,mac,strings[0],strings[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
