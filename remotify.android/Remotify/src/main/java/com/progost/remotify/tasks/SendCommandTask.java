package com.progost.remotify.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.progost.remotify.Client;
import com.progost.remotify.GeneralComputerActivity;

/**
 * Created by Ostap on 5/1/14.
 */
public class SendCommandTask extends ClientAsyncTask<String,Void,String> {

    public SendCommandTask(GeneralComputerActivity activity) {
        super(activity);
    }

    @Override
    protected String execInBackground(String... strings) {
        try {
            return client.simpleCommand(key,mac,strings[0]);
        } catch (Exception e) {
            Log.e("SimpleCommandError","",e);
            return null;
        }
    }
}
