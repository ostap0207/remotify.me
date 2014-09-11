package com.progost.remotify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.progost.remotify.CommandActivity;
import com.progost.remotify.GeneralComputerActivity;
import com.progost.remotify.R;
import com.progost.remotify.ScreenShotActivity;
import com.progost.remotify.commands.Command;
import com.progost.remotify.tasks.ChangeVolumeTask;
import com.progost.remotify.tasks.SendCommandTask;

public class EntertaimentActivity extends NavDrawerComputerActivity implements View.OnClickListener {

    private Button btnPause;
    private Button btnPlay;
    private Button btnFullScreen;
    private Switch switchVolume;
    private SeekBar volumeSeekBar;

    MediaButtonReceiver mediaReceiver = new MediaButtonReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertaiment);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        btnPause = (Button)findViewById(R.id.btnPause);
        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnFullScreen = (Button)findViewById(R.id.btnFullScreen);
        switchVolume = (Switch)findViewById(R.id.switchVolume);
        volumeSeekBar = (SeekBar)findViewById(R.id.volumeSeekBar);

        btnPause.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnFullScreen.setOnClickListener(this);

        switchVolume.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    sendSimpleCommand(Command.UNMUTE);
                }else{
                    sendSimpleCommand(Command.MUTE);
                }
            }
        });

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int value = seekBar.getProgress();
                new ChangeVolumeTask(EntertaimentActivity.this).execute(value);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean result = super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            sendSimpleCommand(Command.VOLUME_DOWN);
        }else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            sendSimpleCommand(Command.VOLUME_UP);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.command, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        Button btn = (Button)view;
        String command = ((String)btn.getTag()).toLowerCase();
        sendSimpleCommand(command);
    }


    private class MediaButtonReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
                KeyEvent ke = (KeyEvent)intent.getExtras().get(Intent.EXTRA_KEY_EVENT);
                if (ke.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) {
                    sendSimpleCommand(Command.VOLUME_DOWN);
                }

                if (ke.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP) {
                    sendSimpleCommand(Command.VOLUME_UP);
                }
            }
        }
    }

}
