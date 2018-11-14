package com.example.admin.facebookdownloader;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class Main2Activity extends BaseAdapter {
    int d,t;
    String Vidname;
    Context context;
    public Main2Activity(Context context, int ds, int ts, String q){
        this.context = context;
        this.d=ds;
        this.t=ts;
        this.Vidname=q;

    }

    @Override
    public int getCount() {
        return 1;
    }

    public Object getItem(int i) {
        return i;
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.activity_main2, parent, false);
            viewHolder.txtName = convertView.findViewById(R.id.aNametxt);
            viewHolder.txtVersion = convertView.findViewById(R.id.aVersiontxt);
            viewHolder.icon = convertView.findViewById(R.id.p);
            viewHolder.pause= convertView.findViewById(R.id.b1);
            viewHolder.resume= convertView.findViewById(R.id.b2);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        float a =(100*((float)d/(float)t));
        viewHolder.txtName.setText(Vidname);
        viewHolder.icon.setMax(t);
        viewHolder.icon.setProgress(d);
        viewHolder.txtVersion.setText(""+(int)a+"%");

        return convertView;
    }
    private static class ViewHolder {

        TextView txtName;
        TextView txtVersion;
        ProgressBar icon;
        Button pause,resume;

    }

}
