package com.example.admin.facebookdownloader;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Random;

public class MainActivity1 extends AppCompatActivity {
    //String urlDownload = "http://techslides.com/demos/sample-videos/small.mp4";
     static ListView lView;
    String p;
    int i;
    long download=0;
    int ds;
    int ts;
    TextView T1;
    static Main2Activity ra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        lView = findViewById(R.id.androidList);

        T1= findViewById(R.id.text);


        if(!StaticData.downloads)
        {
            T1.setVisibility(View.VISIBLE);
        }
        }
    public  void processVideo(final Context context, String v) {
        try {
            StaticData.downloads = true;
            String mBaseFolderPath = Environment
                    .getExternalStorageDirectory()
                    + File.separator
                    + "Facebook Video Downloader" + File.separator;
            if (!new File(mBaseFolderPath).exists()) {
                new File(mBaseFolderPath).mkdir();
            }

            String mFilePath = "file://" + mBaseFolderPath + "/Video" + (new Random(10000)).nextInt() + ".mp4";
            Uri downloadUri = Uri.parse(v);
            DownloadManager.Request req = new DownloadManager.Request(downloadUri);
            req.setDestinationUri(Uri.parse(mFilePath));
            req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            final DownloadManager manager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
            final long downloadId = manager.enqueue(req);
            new Thread(new Runnable() {

                @Override
                public void run() {

                    boolean downloading = true;

                    while (downloading) {

                        DownloadManager.Query q = new DownloadManager.Query();
                        q.setFilterById(downloadId);

                        Cursor cursor = manager.query(q);
                        cursor.moveToFirst();
                        final int bytes_downloaded = cursor.getInt(cursor
                                .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        final int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                        if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                            downloading = false;
                        }runOnUiThread(new Runnable() {



                            public void run()
                            {
                        try {

                            ds = bytes_downloaded;
                            ts = bytes_total;
                            Log.d("Downloadbytes", ""+ds);
                            p= "Video-"+(new Random(10000)).nextInt() + ".mp4";
                            ra = new Main2Activity(context, ds, ts,p);
                            lView.setAdapter(ra);
                            ra.notifyDataSetChanged();
                        }
                        catch (Exception e)
                        {
                            Log.d("Fieldsss", e.toString());
                        }
                            }
                        });
                        cursor.close();
                    }

                }
            }).start();

            Toast.makeText(context, "Download Started", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context, "Download Failed: " , Toast.LENGTH_LONG).show();
        }
    }

}
