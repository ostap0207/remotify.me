package com.progost.remotify.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.progost.remotify.Client;
import com.progost.remotify.GeneralComputerActivity;

/**
 * Created by Ostap on 5/18/2014.
 */
public abstract class ClientAsyncTask<Params, Progress, Result> extends AsyncTask<Params,Progress,Result> {

    protected Client client = new Client();
    protected GeneralComputerActivity activity;
    protected String key;
    protected String mac;

    public ClientAsyncTask(GeneralComputerActivity activity) {
        this.activity = activity;
        this.key = activity.getKey();
        this.mac = activity.getComputer().connectionKey;
    }

    @Override
    protected Result doInBackground(Params... paramses) {
        Log.i("ClientAsyncTask","Task STARTED");
        Result result = execInBackground(paramses);
        Log.i("ClientAsyncTask","Task FINISHED");
        return result;
    }


    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        Log.i("ClientAsyncTask","Task POST_EXEC STARTED");
        onPostExec(result);
        Log.i("ClientAsyncTask","Task POST_EXEC FINISHED");

    }

    protected abstract Result execInBackground(Params... paramses);

    protected void onPostExec(Result result){
    }
}
