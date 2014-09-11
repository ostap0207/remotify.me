package com.progost.remotify;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.progost.remotify.components.adapters.NavDrawerArrayAdapter;
import com.progost.remotify.tasks.KeyboardTask;

import java.util.HashSet;
import java.util.Set;

public class NavDrawerComputerActivity extends GeneralComputerActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    String[] options = {"Presentation","Entertainment", "Screenshots", "Camera", "Explorer"};
    private ListView mDrawerRightList;
    private static Set<String> PAID_PROFILES;

    static{
        PAID_PROFILES = new HashSet<String>();
        if (!BuildConfig.PAID){
            String[] profiles = BuildConfig.PAID_PROFILES.split(",");
            for (String profile: profiles) {
                PAID_PROFILES.add(profile);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.left_drawer);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new NavDrawerArrayAdapter(this, PAID_PROFILES,options));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_navigation_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        );
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.setScrimColor(0x00000000);

        TextView computerName = (TextView) findViewById(R.id.computer_name);
        computerName.setText(getComputer().name);
        initKeyboard();

        overridePendingTransition(0, 0);

    }

    private void initKeyboard(){
        final Button btnAlt = (Button)findViewById(R.id.btnAlt);
        btnAlt.setOnClickListener(new OnFakeStateBtnClickListener(18));

        final Button btnTab = (Button)findViewById(R.id.btnTab);
        btnTab.setOnClickListener(new OnBtnClickListener(9));

        final Button btnCtrl = (Button)findViewById(R.id.btnCtrl);
        btnCtrl.setOnClickListener(new OnFakeStateBtnClickListener(17));

        final Button btnUp = (Button)findViewById(R.id.btnUp);
        btnUp.setOnClickListener(new OnBtnClickListener(38));

        final Button btnDown = (Button)findViewById(R.id.btnDown);
        btnDown.setOnClickListener(new OnBtnClickListener(40));

        final Button btnLeft = (Button)findViewById(R.id.btnLeft);
        btnLeft.setOnClickListener(new OnBtnClickListener(37));

        final Button btnRight = (Button)findViewById(R.id.btnRight);
        btnRight.setOnClickListener(new OnBtnClickListener(39));

        final Button btnEsc = (Button)findViewById(R.id.btnEsc);
        btnEsc.setOnClickListener(new OnBtnClickListener(27));

        final Button btnCapsLock = (Button)findViewById(R.id.btnCapsLock);
        btnCapsLock.setOnClickListener(new OnStateBtnClickListener(20));

        final Button btnShift = (Button)findViewById(R.id.btnShift);
        btnShift.setOnClickListener(new OnFakeStateBtnClickListener(16));

        for (int i = 0;i < 12; i++){
            Button btnF = (Button)findViewByStringId("btnF" + (i + 1));
            btnF.setOnClickListener(new OnBtnClickListener(112 + i));
        }

        final Button btnEnter = (Button)findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(new OnBtnClickListener(10));

        final Button btnPopup = (Button)findViewById(R.id.btnPopup);
        btnPopup.setOnClickListener(new OnBtnClickListener(525));

        final Button btnBackspace = (Button)findViewById(R.id.btnBackspace);
        btnBackspace.setOnClickListener(new OnBtnClickListener(8));
    }

    private View findViewByStringId(String stringId){
        int id = getResources().getIdentifier(stringId, "id", getPackageName());
        return findViewById(id);
    }


    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.action_keyboard){
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)){
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
            }else{
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        Intent intent = null;
        if (PAID_PROFILES.contains(options[position])){
            return;
        }
        switch (position) {
            case 0:
                intent = new Intent(this, CommandActivity.class);
                break;
            case 1:
                intent = new Intent(this, EntertaimentActivity.class);
                break;
            case 2:
                intent = new Intent(this, ScreenShotActivity.class);
                intent.putExtra("type",ScreenShotActivity.Type.SCREEN.toString());
                break;
            case 3:
                intent = new Intent(this, ScreenShotActivity.class);
                intent.putExtra("type",ScreenShotActivity.Type.CAMERA.toString());
                break;
            case 4:
                intent = new Intent(this, ExplorerActivity.class);
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
                    finalIntent.putExtra("computer", getComputer());
                    startActivity(finalIntent);
                    finish();
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


    private abstract class GeneralBtnClickListener implements View.OnClickListener{

        protected int key;
        protected static final String RELEASED = "released";
        protected static final String PRESSED = "pressed";
        protected StyleSpan span = new StyleSpan(Typeface.BOLD);

        private GeneralBtnClickListener(int key) {
            this.key = key;
        }

        protected void updateBtnText(Button btn, String newState){
            String text = btn.getText().toString();
            SpannableString spanString = new SpannableString(text);
            btn.setTag(newState);
            if (newState.equals(RELEASED)){
                spanString.removeSpan(span);
            }else{
                spanString.setSpan(span, 0, spanString.length(), 0);
            }
            btn.setText(spanString);
        }

    }

    private class OnStateBtnClickListener extends GeneralBtnClickListener{

        private OnStateBtnClickListener(int key) {
            super(key);
        }

        @Override
        public void onClick(View view) {
            final Button btn = (Button)view;
            if (btn.getTag() == null) btn.setTag(RELEASED);
            String state = (String)view.getTag();
            final String newState;
            if (state.equals(RELEASED)){
                newState = PRESSED;
            }else{
                newState = RELEASED;
            }
            KeyboardTask task = new KeyboardTask(NavDrawerComputerActivity.this){
                @Override
                protected void onPostExec(String s) {
                    btn.setEnabled(true);
                    updateBtnText(btn,newState);
                }
            };
            btn.setEnabled(false);
            task.execute("type",Integer.toString(key));
        }
    }

    private class OnFakeStateBtnClickListener extends GeneralBtnClickListener{

        private OnFakeStateBtnClickListener(int key) {
            super(key);
        }

        @Override
        public void onClick(View view) {
            final Button btn = (Button)view;
            if (btn.getTag() == null) btn.setTag(RELEASED);
            String state = (String)view.getTag();
            String action;
            final String newState;
            if (state.equals(RELEASED)){
                action = "press";
                newState = PRESSED;
            }else{
                action = "release";
                newState = RELEASED;
            }
            KeyboardTask task = new KeyboardTask(NavDrawerComputerActivity.this){
                @Override
                protected void onPostExec(String s) {
                    btn.setEnabled(true);
                    updateBtnText(btn,newState);
                }
            };
            btn.setEnabled(false);
            task.execute(action,Integer.toString(key));
        }

    }

    private class OnBtnClickListener extends GeneralBtnClickListener{

        private OnBtnClickListener(int key) {
            super(key);
        }

        @Override
        public void onClick(View view) {
            KeyboardTask task = new KeyboardTask(NavDrawerComputerActivity.this);
            task.execute("type",Integer.toString(key));
        }
    }

}
