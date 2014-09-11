package com.progost.remotify.components.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.progost.remotify.BuildConfig;
import com.progost.remotify.R;
import com.progost.remotify.entity.Computer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ostap on 7/10/2014.
 */
public class NavDrawerArrayAdapter extends ArrayAdapter<String> {

    private final String[] profiles;
    private LayoutInflater inflater;
    private Set<String> PAID_PROFILES ;


    public NavDrawerArrayAdapter(Context context, Set<String> PAID_PROFILES, String... profiles) {
        super(context, R.layout.drawer_list_item_view, profiles);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.profiles = profiles;
        this.PAID_PROFILES = PAID_PROFILES;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null){
            rowView = inflater.inflate(R.layout.drawer_list_item_view, null);
        }

        TextView textView = (TextView)rowView;
        String name = profiles[position];
        if (PAID_PROFILES.contains(name)){
            textView.setTextColor(Color.GRAY);
            textView.setEnabled(false);
            name += " [PRO]";
        }

        textView.setText(name);

        return textView;
    }
}
