package com.example.admin.facebookdownloader;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class All_Downloads extends AppCompatActivity {
    private static final String WHATSAPP_STATUSES_LOCATION = "/storage/emulated/0/FaceBook Video Downloader";
     ListView lView;
     ListAdapter ra;
     TextView T1;
     int position;
Button delete;
    File filee;
    public List<File> filess=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_downloads);
        T1= findViewById(R.id.text);

        delete= findViewById(R.id.delete);
        lView = findViewById(R.id.androidList);
        filess=getListFiles(new File(WHATSAPP_STATUSES_LOCATION));
        if(filess.size()==0)
        {
           T1.setVisibility(View.VISIBLE);
        }
         ra = new ListAdapter(All_Downloads.this, (ArrayList<File>) this.filess);
        lView.setAdapter(ra);
        lView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final File file = ListAdapter.files.get(i);
                if(file.getAbsolutePath().contains("mp4")||(file.getAbsolutePath().contains("gif"))) {
                    Intent intent = new Intent(getApplicationContext(), StreamVideo.class);
                    intent.putExtra("video_url", file.getAbsoluteFile().toString());
                    startActivity(intent);
                }

                else
                {
                    Intent intent = new Intent(getApplicationContext(), Download_previewpix.class);
                    intent.putExtra("video_url", file.getAbsoluteFile().toString());
                    startActivity(intent);
                }
            }
        });
        lView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,
                                           int pos, long id) {
              // Toast.makeText(getApplicationContext(),"hahaahha",Toast.LENGTH_SHORT).show();
                position = pos;
                filee=ListAdapter.files.get(pos);
            delete.setVisibility(View.VISIBLE);
                return true;
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filee.delete();
                filess.remove(position);
                ra.notifyDataSetChanged();
                delete.setVisibility(View.GONE);
            }
        });


    }
    private ArrayList<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files;
        files = parentDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".jpg") ||
                        file.getName().endsWith(".png") ||
                        file.getName().endsWith(".jpeg")||file.getName().endsWith(".mp4") ||
                        file.getName().endsWith(".gif")) {
                    if (!inFiles.contains(file))
                        inFiles.add(file);
                }
            }
        }
        return inFiles;
    }



}