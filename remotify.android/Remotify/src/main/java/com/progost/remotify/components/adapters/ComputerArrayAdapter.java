package com.progost.remotify.components.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.progost.remotify.ComputerActivity;
import com.progost.remotify.R;
import com.progost.remotify.entity.Computer;

import java.util.List;

/**
 * Created by Ostap on 5/3/14.
 */
public class ComputerArrayAdapter extends ArrayAdapter<Computer> {

    private final Context context;
    private final List<Computer> computers;
    private LayoutInflater inflater;

    static class ViewHolder {
        public ImageView icon;
        public ImageView statusIcon;
        public TextView name;
    }

    public ComputerArrayAdapter(Context context,List<Computer> computers) {
        super(context, R.layout.computer_row, computers);
        this.context = context;
        this.computers = computers;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null){
            rowView = inflater.inflate(R.layout.computer_row, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView)rowView.findViewById(R.id.icon);
            viewHolder.statusIcon = (ImageView)rowView.findViewById(R.id.statusIcon);
            viewHolder.name = (TextView)rowView.findViewById(R.id.computer_name);
            rowView.setTag(viewHolder);
        }

        final Computer computer = computers.get(position);
        ViewHolder viewHolder = (ViewHolder)rowView.getTag();
        boolean online = computer.status == Computer.Status.ONLINE;
        viewHolder.statusIcon.setImageResource(online ? R.drawable.online : R.drawable.offline);
        viewHolder.name.setText(computer.name);
        return rowView;
    }
}
