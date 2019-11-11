package com.example.pramila.eas;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListView extends ArrayAdapter<String> {

    private String[] eventname;
    private String[] postdate;
    private Activity context;

    public CustomListView(Activity context,String[] eventname,String[] postdate) {
        super(context, R.layout.layout, eventname);
        this.context=context;
        this.eventname=eventname;
        this.postdate=postdate;
    }


    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.layout, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();

        }

        viewHolder.tvw1.setText(eventname[position]);
        viewHolder.tvw2.setText(postdate[position]);


        return r;
    }

    class ViewHolder {

        TextView tvw1;
        TextView tvw2;


        ViewHolder(View v) {
            tvw1 = (TextView) v.findViewById(R.id.tveventname);
            tvw2 = (TextView) v.findViewById(R.id.tvpostname);
        }
    }
}
