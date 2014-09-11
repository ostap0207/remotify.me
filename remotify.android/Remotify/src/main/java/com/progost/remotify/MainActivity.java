package com.progost.remotify;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends FragmentActivity{

    public static String AUTH = "auth";
    protected String key;

    public Client getApi() {
        return api;
    }

    public void setApi(Client api) {
        this.api = api;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    protected Client api = new Client();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        key = getSharedPreferences(AUTH,0).getString("key",null);
        if (key == null){
            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);
            finish();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_logout:
                logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout(){
        getSharedPreferences(AUTH,0).edit().clear().commit();
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
    }

    protected boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT);
            return false;
        } else
            return true;
    }

    protected void goToNoConnectionActivity(){
        Intent i = new Intent(this, NoConnectionActivity.class);
        startActivity(i);
    }
}
