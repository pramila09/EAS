package com.example.pramila.eas;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAttendanceView extends ArrayAdapter<String> {
    private String[] enrolldate;
    private String[] enrolltime;
    private Activity context;

    public CustomAttendanceView(Activity context,String[] enrolldate,String[] enrolltime) {
        super(context, R.layout.layout3, enrolldate);
        this.context=context;
        this.enrolldate=enrolldate;
        this.enrolltime=enrolltime;
    }


    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.layout3, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();

        }

        viewHolder.tvw1.setText(enrolldate[position]);
        viewHolder.tvw2.setText(enrolltime[position]);


        return r;
    }

    class ViewHolder {

        TextView tvw1;
        TextView tvw2;


        ViewHolder(View v) {
            tvw1 = (TextView) v.findViewById(R.id.tvenrolldate);
            tvw2 = (TextView) v.findViewById(R.id.tvenrolltime);
        }
    }
}
