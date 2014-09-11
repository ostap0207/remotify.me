package com.progost.remotify;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.progost.remotify.components.adapters.ComputerArrayAdapter;
import com.progost.remotify.entity.Computer;

import java.util.ArrayList;
import java.util.List;


public class ShareActivity extends MainActivity {


    private Computer computer;
    private Button shareBtn;
    private Spinner spinner;
    private ArrayAdapter<Computer> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        final String url = intent.getData().toString();
        ((TextView)findViewById(R.id.url)).setText(url);

        shareBtn = (Button)findViewById(R.id.share_button);
        shareBtn.setEnabled(false);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (computer != null){
                    new BrowseTask().execute(key,computer.connectionKey,url);
                    Intent commands = new Intent(ShareActivity.this, ComputerActivity.class);
                    commands.putExtra("computer", computer);
                    startActivity(commands);
                    finish();
                }
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                computer = arrayAdapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                computer = null;
            }
        });

        if (computer == null){
            new LoadComputersTask().execute(key);
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class LoadComputersTask extends AsyncTask<String,Void,List<Computer>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!isNetworkConnected()){
                goToNoConnectionActivity();
            }
        }

        @Override
        protected List<Computer> doInBackground(String... strings) {
            Client client = new Client();
            try {
                return client.getComputers(strings[0]);
            } catch (Exception e) {
                return null;
            }
        }


        @Override
        protected void onPostExecute(List<Computer> computers) {
            if (computers != null) {

                if (computers.size() == 0) {
                    goToAddcomputerActivity(true);
                }else{
                    arrayAdapter = new ArrayAdapter<Computer>(ShareActivity.this,android.R.layout.simple_spinner_item,filterOnline(computers));
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(arrayAdapter);
                    shareBtn.setEnabled(true);
                }

            }
        }

        protected List<Computer> filterOnline(List<Computer> computers){
            List<Computer> result = new ArrayList<Computer>();
            for (Computer computer : computers) {
                if (computer.status == Computer.Status.ONLINE){
                    result.add(computer);
                }

            }
            return result;
        }

    }

    private void goToAddcomputerActivity(boolean finish){
        Intent i = new Intent(ShareActivity.this, AddComputerActivity.class);
        startActivity(i);
        if (finish)
            finish();
    }

    public static class BrowseTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String status) {
            super.onPostExecute(status);
        }

        @Override
        protected String doInBackground(String... strings) {
            Client client = new Client();
            try {
                return client.browser(strings[0],strings[1],strings[2]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }


}
