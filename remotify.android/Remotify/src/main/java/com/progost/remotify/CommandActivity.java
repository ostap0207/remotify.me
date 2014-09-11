package com.progost.remotify;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.progost.remotify.adapters.CommandsTabAdapter;
import com.progost.remotify.fragments.CommandsFragment;
import com.progost.remotify.fragments.ScreenFragment;
import com.progost.remotify.tasks.SendCommandTask;

import java.util.ArrayList;
import java.util.List;

public class CommandActivity extends NavDrawerComputerActivity implements ActionBar.TabListener{

    private ViewPager viewPager;
    private ActionBar actionBar;
    private CommandsTabAdapter mAdapter;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commands);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        fragments = new ArrayList<Fragment>();
        fragments.add(Fragment.instantiate(this, CommandsFragment.class.getName(),savedInstanceState));
        fragments.add(Fragment.instantiate(this, ScreenFragment.class.getName(),savedInstanceState));

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new CommandsTabAdapter(getSupportFragmentManager(),this, fragments);
        viewPager.setAdapter(mAdapter);
        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        actionBar.addTab(actionBar.newTab().setText("Commands")
                    .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Screen")
                    .setTabListener(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.command, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean result = super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            new SendCommandTask(this).execute("left");
        }

        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            new SendCommandTask(this).execute("right");
        }
        updateFragments();
        return true;
    }

    public void updateFragments(){
        ((ScreenFragment)fragments.get(1)).loadScreen(this);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
