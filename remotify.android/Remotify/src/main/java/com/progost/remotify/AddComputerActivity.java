package com.progost.remotify;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class AddComputerActivity extends MainActivity {

    private ImageButton btnAdd;
    private View statusView;
    private View addComputerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_computer);

        btnAdd = (ImageButton)findViewById(R.id.addComputer);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddComputerActivity.this, CaptureActivity.class);
                intent.putExtra("com.google.zxing.client.android.SCAN", "QR_CODE_MODE");
                startActivityForResult(intent, 0);
            }
        });

        statusView = findViewById(R.id.add_computer_status_view);
        addComputerView = findViewById(R.id.add_computer_view);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Log.i("xZing", "contents: " + contents + " format: " + format);

                String key = getSharedPreferences(MainActivity.AUTH,0).getString("key", null);
                if (key != null){
                    AddComputerTask task = new AddComputerTask();
                    task.execute(key,contents);
                }

            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
                Log.i("xZing", "Cancelled");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_computer, menu);
        return true;
    }

    private class AddComputerTask extends AsyncTask<String,Void,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            statusView.setVisibility(View.VISIBLE);
            addComputerView.setVisibility(View.GONE);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            Client client = new Client();
            try {
                return client.addComputer(strings[0],strings[1]);
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
                return false;
            }
        }


        @Override
        protected void onPostExecute(Boolean success) {
            if (success){
                Intent i = new Intent(AddComputerActivity.this, ComputersActivity.class);
                startActivity(i);
                finish();
            }else{
                statusView.setVisibility(View.GONE);
                addComputerView.setVisibility(View.VISIBLE);
                Toast.makeText(AddComputerActivity.this,"Error while adding computer or computer already connected",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
