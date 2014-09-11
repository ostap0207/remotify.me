package com.progost.remotify.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.progost.remotify.Client;
import com.progost.remotify.GeneralComputerActivity;
import com.progost.remotify.R;
import com.progost.remotify.commands.Command;
import com.progost.remotify.tasks.ScreenShotTask;
import com.progost.remotify.tasks.SendCommandTask;

public class ScreenFragment extends Fragment {

    private GeneralComputerActivity activity;
    private ImageView mScreenShotView;
    private Button nextBtn;
    private Button prevBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_screen_shot, container, false);
        activity = (GeneralComputerActivity) getActivity();
        mScreenShotView = (ImageView)rootView.findViewById(R.id.screen_shot_view);
        nextBtn = (Button)rootView.findViewById(R.id.nextBtn);
        prevBtn = (Button)rootView.findViewById(R.id.prevBtn);
        nextBtn.setOnClickListener(new SlideChangeListener("right"));
        prevBtn.setOnClickListener(new SlideChangeListener("left"));

        return rootView;
    }



    @Override
    public void onResume() {
        super.onResume();
        loadScreen(activity);
    }

    public void loadScreen(GeneralComputerActivity activity){
        if (mScreenShotView != null) {
            new ScreenShotTask(activity, mScreenShotView).execute();
        }
    }

    private class SlideChangeListener implements View.OnClickListener{

        private String command;

        public SlideChangeListener(String command) {
            this.command = command;

        }

        @Override
        public void onClick(View view) {
            new SendCommandTask(activity).execute(command);
            loadScreen(activity);
        }
    }

}
