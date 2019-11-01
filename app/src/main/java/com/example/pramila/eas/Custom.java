package com.example.pramila.eas;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Custom extends ArrayAdapter<String> {

    private String[] name;
    private String[] email;
    private String[] address;
    private String[] dept;
    private String[] reg;
    private Activity context;

    public Custom(Activity context, String[] name, String[] email, String[] address, String[] dept, String[] reg) {
        super(context, R.layout.activity_profile, name);
        this.context = context;
        this.name = name;
        this.email = email;
        this.address = address;
        this.dept = dept;
        this.reg = reg;
    }

    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.activity_profile, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();

        }

        viewHolder.tv1.setText(name[position]);
        viewHolder.tv2.setText(email[position]);
        viewHolder.tv3.setText(address[position]);
        viewHolder.tv4.setText(dept[position]);
        viewHolder.tv5.setText(reg[position]);
        return r;
    }

    class ViewHolder{
        TextView tv1,tv2,tv3,tv4,tv5;
        ViewHolder(View v){
            tv1=(TextView)v.findViewById(R.id.tvname);
            tv2=(TextView)v.findViewById(R.id.tvemail);
            tv3=(TextView)v.findViewById(R.id.tvaddress);
            tv4=(TextView)v.findViewById(R.id.tvdept);
            tv5=(TextView)v.findViewById(R.id.tvreg);
        }
    }
}
