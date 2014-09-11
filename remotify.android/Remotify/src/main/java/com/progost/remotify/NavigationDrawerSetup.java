package com.progost.remotify;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by User on 30.05.2014.
 */
public class NavigationDrawerSetup {

    private GeneralComputerActivity activity;
    String[] options = {"Presentation","Entertainment", "Screenshots", "Camera", "Explorer"};

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public ListView getDrawerList() {
        return mDrawerList;
    }

    public ActionBarDrawerToggle getDrawerToggle() {
        return mDrawerToggle;
    }

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    public NavigationDrawerSetup(GeneralComputerActivity activity) {
        this.activity = activity;
    }

    public void init(){
        mDrawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) activity.findViewById(R.id.left_drawer);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(activity,R.layout.drawer_list_item_view, options));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(
                activity,
                mDrawerLayout,
                R.drawable.ic_navigation_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        );
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(activity, CommandActivity.class);
                break;
            case 1:
                intent = new Intent(activity, EntertaimentActivity.class);
                break;
            case 2:
                intent = new Intent(activity, ScreenShotActivity.class);
                intent.putExtra("type",ScreenShotActivity.Type.SCREEN.toString());
                break;
            case 3:
                intent = new Intent(activity, ScreenShotActivity.class);
                intent.putExtra("type",ScreenShotActivity.Type.CAMERA.toString());
                break;
            case 4:
                intent = new Intent(activity, ExplorerActivity.class);
                break;
        }

        if (intent != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Handler handler = new Handler();
            final Intent finalIntent = intent;
            handler.postDelayed(new Runnable() {
                public void run() {
                    finalIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finalIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finalIntent.putExtra("computer", activity.getComputer());
                    activity.startActivity(finalIntent);
                }
            }, 150);

        }

    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}
