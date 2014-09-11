package com.progost.remotify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.progost.remotify.commands.Command;


public class ExplorerActivity extends NavDrawerComputerActivity {

    private Button nextWindowBtn;
    private Button closeWindowBtn;
    private Button sendBtn;
    private EditText urlText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer);

        urlText = (EditText) findViewById(R.id.editText);
        nextWindowBtn = (Button)findViewById(R.id.nextWindowBtn);
        nextWindowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSimpleCommand(Command.NEXT_WINDOW);
            }
        });

        closeWindowBtn = (Button)findViewById(R.id.closeWindowBtn);
        closeWindowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSimpleCommand(Command.CLOSE_WINDOW);
            }
        });

        sendBtn = (Button)findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ShareActivity.BrowseTask().execute(key,mComputer.connectionKey,urlText.getText().toString());
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.explorer, menu);
        return true;
    }

}
