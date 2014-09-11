package com.progost.remotify;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.progost.remotify.commands.Command;
import com.progost.remotify.entity.Computer;
import com.progost.remotify.tasks.SendCommandTask;

import java.util.List;

/**
 * Created by Ostap on 9/15/13.
 */
public class GeneralComputerActivity extends MainActivity{

    protected Computer mComputer;

    public Computer getComputer() {
        return mComputer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mComputer = getIntent().getParcelableExtra("computer");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mComputer = savedInstanceState.getParcelable("computer");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("computer",mComputer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadComputer();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void loadComputer(){
        new LoadComputerTask().execute(mComputer.connectionKey,key);
    }

    private class LoadComputerTask extends AsyncTask<String,Void,Computer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!isNetworkConnected()){
                goToNoConnectionActivity();
            }
        }

        @Override
        protected Computer doInBackground(String... strings) {
            try {
                return api.getComputer(strings[0],strings[1]);
            } catch (Exception e) {
                return null;
            }
        }


    @Override
    protected void onPostExecute(Computer computer) {
            if (computer != null) {
                mComputer = computer;
            }
        }
    }

    protected void sendSimpleCommand(String command){
        new SendCommandTask(this).execute(command);
    }

    protected void sendSimpleCommand(Command command){
        sendSimpleCommand(command.toString());
    }

}
