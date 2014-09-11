package com.progost.remotify;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ComputerActivity extends NavDrawerComputerActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.computer_drawer);

//        TextView computerName = (TextView)findViewById(R.id.computer_name);
//        computerName.setText(mComputer.name + "\t" + mComputer.status);
//
//        ImageView computerImage = (ImageView) findViewById(R.id.computer_image);
//
//        //init list
//        ListView controlOptions = (ListView) findViewById(R.id.control_options);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,options);
//        controlOptions.setAdapter(arrayAdapter);
//        controlOptions.setOnItemClickListener(this);
        Intent controlIntent = new Intent(this,CommandActivity.class);
        controlIntent.putExtra("computer", mComputer);
        controlIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(controlIntent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.computer, menu);
        return true;
    }

}
