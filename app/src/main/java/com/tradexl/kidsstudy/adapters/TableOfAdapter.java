package com.tradexl.kidsstudy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.tradexl.kidsstudy.R;

import java.util.List;

/**
 * Created by Raghav on 27-Dec-17.
 */

public class TableOfAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private LayoutInflater inflater;

    public TableOfAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.indexOf(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View myView = inflater.inflate(R.layout.table_of_row,null, false);
        TextView textView=(TextView)myView.findViewById(R.id.table_name);
        textView.setText(list.get(position).toString());
        return myView;
    }
}
