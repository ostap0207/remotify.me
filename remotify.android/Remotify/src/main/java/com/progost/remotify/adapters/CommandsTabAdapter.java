package com.progost.remotify.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.progost.remotify.CommandActivity;
import com.progost.remotify.fragments.CommandsFragment;
import com.progost.remotify.fragments.ScreenFragment;

import java.util.List;

/**
 * Created by Ostap on 5/19/2014.
 */
public class CommandsTabAdapter extends FragmentPagerAdapter {

    private final CommandActivity commandActivity;
    private List<Fragment> fragments;

    public CommandsTabAdapter(FragmentManager fm, CommandActivity commandActivity, List<Fragment> fragments) {
        super(fm);
        this.commandActivity = commandActivity;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
