package com.progost.remotify;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;

import com.progost.remotify.components.adapters.ComputerArrayAdapter;
import com.progost.remotify.entity.Computer;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ComputersActivity extends MainActivity {

    private ListView computersList;
    private ProgressBar progressBar;
    private ArrayAdapter<Computer> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computers);

        computersList = (ListView)findViewById(R.id.computers_list);
        progressBar = (ProgressBar)findViewById(R.id.computers_progress);

        computersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent commands = new Intent(ComputersActivity.this, ComputerActivity.class);
                commands.putExtra("computer", arrayAdapter.getItem(i));
                startActivity(commands);
            }
        });

        computersList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                PopupMenu popup = new PopupMenu(ComputersActivity.this,view);
                popup.getMenu().add("Delete");
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        try {
                            new DeleteComputersTask().execute(key, arrayAdapter.getItem(i).connectionKey);
                        } catch (Exception e) {
                             e.printStackTrace();
                        }
                        return true;
                    }
                });
                popup.show();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_add:
                goToAddcomputerActivity(false);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    protected void update(){
        LoadComputersTask loadComputersTask = new LoadComputersTask();
        loadComputersTask.execute(key);
    }

    private class DeleteComputersTask extends AsyncTask<String,Void,Boolean>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!isNetworkConnected()){
                goToNoConnectionActivity();
            }
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            Client client = new Client();
            try {
                api.deleteComputer(strings[0],strings[1]);
            } catch (Exception e) {
            }
            return true;
        }


        @Override
        protected void onPostExecute(Boolean computers) {
            update();
        }


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
                Log.e("ERROR","Error while loading computers",e);
                return null;
            }
        }


            @Override
            protected void onPostExecute(List<Computer> computers) {
            if (computers != null) {

                if (computers.size() == 0) {
                    goToAddcomputerActivity(true);

                }

                arrayAdapter = new ComputerArrayAdapter(ComputersActivity.this
                        ,computers);

                computersList.setAdapter(arrayAdapter);
                progressBar.setVisibility(View.GONE);
            }
        }


    }


    private void goToAddcomputerActivity(boolean finish){
        Intent i = new Intent(ComputersActivity.this, AddComputerActivity.class);
        startActivity(i);
        if (finish)
            finish();
    }
}
