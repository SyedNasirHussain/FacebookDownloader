package com.example.admin.facebookdownloader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ListAdapter extends BaseAdapter {

    Context context;
    public static List<File> files=new ArrayList<>();
    public ListAdapter(Context context, ArrayList<File> file){
        this.context = context;
        files = file;
    }

    @Override
    public int getCount() {
        return files.size();
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
            convertView = inflater.inflate(R.layout.downoad_details, parent, false);
            viewHolder.txtName = convertView.findViewById(R.id.aNametxt);
            viewHolder.txtVersion = convertView.findViewById(R.id.aVersiontxt);
            viewHolder.icon = convertView.findViewById(R.id.appIconIV);
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        final File currentFile = files.get(position);
       Glide.with(context).load(currentFile.getAbsolutePath())
                .skipMemoryCache(false)
                .crossFade().into(viewHolder.icon);
        viewHolder.txtName.setText(currentFile.getName());
        if(currentFile.getAbsolutePath().contains("mp4"))
            viewHolder.txtVersion.setText("Video");
        else
            viewHolder.txtVersion.setText("Image");


        return convertView;
    }

    private static class ViewHolder {

        TextView txtName;
        TextView txtVersion;
        ImageView icon;

    }

}