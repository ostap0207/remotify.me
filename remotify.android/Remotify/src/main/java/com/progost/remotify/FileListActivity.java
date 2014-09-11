package com.progost.remotify;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class FileListActivity extends GeneralComputerActivity implements AdapterView.OnItemClickListener {

    private String[] paths;
    private String dirPath;
    private ListView fileListView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filelist);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD )
            getActionBar().setDisplayHomeAsUpEnabled(true);

        dirPath = getIntent().getStringExtra("dirPath");
        if (!dirPath.isEmpty())
            setTitle(dirPath);

        fileListView = (ListView) findViewById(R.id.file_listView);
        fileListView.setOnItemClickListener(this);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        new FileListTask().execute(key,mComputer.connectionKey,dirPath);
        overridePendingTransition(0, 0);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.file_list, menu);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent fileListIntent = new Intent(FileListActivity.this,FileListActivity.class);
        fileListIntent.putExtra("dirPath", paths[i]);
        fileListIntent.putExtra("computer", mComputer);
        startActivity(fileListIntent);
    }

    private class FileListTask extends AsyncTask<String,Void,JSONObject> {
        @Override
        protected JSONObject doInBackground(String... strings) {
            Client client = new Client();
            String path = strings[2];
            try {
            path = URLEncoder.encode(path, HTTP.UTF_8);
            System.out.println("ENCODED = " + path);
                return client.fileList(strings[0], strings[1], path);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject jsonObj) {
            if (jsonObj != null){
                try {
                    String status = jsonObj.getString("status");
                    if (status != null && status.equals("OK")){
                        JSONArray jsonFiles = jsonObj.getJSONArray("data");
                        String[] files = new String[jsonFiles.length()];
                        paths = new String[jsonFiles.length()];
                        for (int i = 0;i < files.length;i++){
                            System.out.println(jsonFiles.getJSONObject(i).getString("path"));
                            files[i] = URLDecoder.decode(jsonFiles.getJSONObject(i).getString("name"),HTTP.UTF_8);
                            paths[i] = URLDecoder.decode(jsonFiles.getJSONObject(i).getString("path"), HTTP.UTF_8);
                            System.out.println(files[i]);
                            System.out.println(paths[i]);
                        }

                        ArrayAdapter aa = new ArrayAdapter(FileListActivity.this,android.R.layout.simple_list_item_1,files);
                        fileListView.setAdapter(aa);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(jsonObj);
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,CommandActivity.class);
        intent.putExtra("computer",mComputer);
        NavUtils.navigateUpTo(this,intent);
    }
}
