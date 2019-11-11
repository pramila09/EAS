package com.example.pramila.eas;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomLeaveView extends ArrayAdapter<String> {
    private final String[] Fromdate;
    private final String[] Todate;
    private final String[] Leavetype;
    private final String[] Description;
    private final String[] Status;
    private final Activity context;

    public CustomLeaveView(Activity context,String[] Fromdate,String[] Todate, String[] Leavetype, String[] Description,String[] Status) {
        super(context, R.layout.layout2, Fromdate);
        Log.e("eas", "fromdate"+Fromdate.length);
        this.context=context;
        this.Fromdate=Fromdate;
        this.Todate=Todate;
        this.Leavetype=Leavetype;
        this.Description=Description;
        this.Status=Status;
    }


    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.layout2, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();

        }

        viewHolder.tvw1.setText(Fromdate[position]);
        viewHolder.tvw2.setText(Todate[position]);
        viewHolder.tvw3.setText(Leavetype[position]);
        viewHolder.tvw4.setText(Description[position]);
        viewHolder.tvw5.setText(Status[position]);
        return r;
    }


    class ViewHolder {

        TextView tvw1;
        TextView tvw2;
        TextView tvw3;
        TextView tvw4;
        TextView tvw5;


        ViewHolder(View v) {
            tvw1 = (TextView) v.findViewById(R.id.tvfromdate);
            tvw2 = (TextView) v.findViewById(R.id.tvtodate);
            tvw3 = (TextView) v.findViewById(R.id.tvleavetype);
            tvw4 = (TextView) v.findViewById(R.id.tvdescription);
            tvw5 = (TextView) v.findViewById(R.id.tvstatus);
        }

    }
}
