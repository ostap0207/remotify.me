package com.progost.remotify.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.progost.remotify.FileListActivity;
import com.progost.remotify.GeneralComputerActivity;
import com.progost.remotify.R;
import com.progost.remotify.commands.Command;
import com.progost.remotify.tasks.SendCommandTask;

public class CommandsFragment extends Fragment implements View.OnClickListener{



    private Button btnRight;
    private Button btnLeft;
    private Button btnSpace;
    private Button btnEnter;
    private Button btnFileList;
    private Button btnStart;

    private GeneralComputerActivity activity;
    private OnSlideChangeListener onSlideChangeListener;

    public OnSlideChangeListener getOnSlideChangeListener() {
        return onSlideChangeListener;
    }

    public void setOnSlideChangeListener(OnSlideChangeListener onSlideChangeListener) {
        this.onSlideChangeListener = onSlideChangeListener;
    }

    public static interface OnSlideChangeListener{
        enum Direction{
            PREV,NEXT
        }
        public void onSlideChanged(Direction direction);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_commands, container, false);
        activity = (GeneralComputerActivity) getActivity();

        btnRight = (Button)rootView.findViewById(R.id.button4);
        btnLeft = (Button)rootView.findViewById(R.id.tryBtn);
        btnSpace = (Button)rootView.findViewById(R.id.button5);
        btnEnter = (Button)rootView.findViewById(R.id.button2);
        btnFileList = (Button)rootView.findViewById(R.id.file_list);
        btnStart = (Button)rootView.findViewById(R.id.startBtn);
        btnEnter.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        btnLeft.setOnClickListener(this);
        btnSpace.setOnClickListener(this);

        btnFileList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fileListIntent = new Intent(getActivity(), FileListActivity.class);
                fileListIntent.putExtra("dirPath", "");
                fileListIntent.putExtra("computer",activity.getComputer());
                startActivity(fileListIntent);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SendCommandTask(activity).execute(Command.F5.toString());
            }
        });

        return rootView;
    }

    @Override
    public void onClick(View view) {
        Button btn = (Button)view;
        String command = btn.getText().toString().toLowerCase();
        if (command.equals(Command.RIGHT.toString().toLowerCase()) && onSlideChangeListener != null){
            onSlideChangeListener.onSlideChanged(OnSlideChangeListener.Direction.NEXT);

        }
        if (command.equals(Command.LEFT.toString().toLowerCase()) && onSlideChangeListener != null){
            onSlideChangeListener.onSlideChanged(OnSlideChangeListener.Direction.PREV);

        }
        new SendCommandTask(activity).execute(command);
    }



}
