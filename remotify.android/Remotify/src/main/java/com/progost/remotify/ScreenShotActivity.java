package com.progost.remotify;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.progost.remotify.tasks.ScreenShotTask;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ScreenShotActivity extends NavDrawerComputerActivity {

    private ProgressBar progressBar;

    enum Type{
        SCREEN, CAMERA
    }

    private AtomicBoolean streaming  = new AtomicBoolean(false);
    private AtomicBoolean next  = new AtomicBoolean(true);

    private ImageView mScreenShotView;
    private Type type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shot);
        mScreenShotView = (ImageView)findViewById(R.id.screen_shot_view);
        type = Type.valueOf(getIntent().getExtras().getString("type"));

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("Start streaming");
        beginStreaming();
    }

    @Override
    protected void onStop() {
        System.out.println("Stop streaming");
        stopStreaming();
        super.onStop();
    }

    private void stopStreaming(){
        streaming.set(false);
    }

    private void beginStreaming(){
        streaming.set(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (streaming.get()) {
                    if (next.compareAndSet(true,false)){
                        System.out.println("Streaming");
                        loadScreen();
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sceen_shot, menu);
        return true;
    }


    private void loadScreen(){
        new RepeatableScreenShotTask(this,mScreenShotView).execute();
    }

    public class RepeatableScreenShotTask extends ScreenShotTask {

        public RepeatableScreenShotTask(GeneralComputerActivity activity, ImageView mScreenShotView) {
            super(activity, mScreenShotView);
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);
            next.set(true);
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected byte[] execInBackground(Void... strings) {
            Client client = new Client();
            try {
                switch (type){
                    case CAMERA:
                        return client.camera(key,mac);
                    case SCREEN:
                        return client.screenShot(key,mac);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
