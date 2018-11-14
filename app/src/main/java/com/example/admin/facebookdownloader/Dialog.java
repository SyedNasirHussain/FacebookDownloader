package com.example.admin.facebookdownloader;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.File;

public class Dialog extends AppCompatActivity {

    MainActivity1 obj;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obj = new MainActivity1();
        Intent intent = getIntent();
        final String vidData = intent.getStringExtra("vid_data");
        final String vidID = intent.getStringExtra("vid_id");
                downloadFB(vidData, vidID);
        obj.processVideo(Dialog.this,vidData);
        StaticData.URL1=vidData;
       Intent in=new Intent(getApplicationContext(), Download.class);
       in.putExtra("URL",vidData);
       startActivity(in);
              //  OtherURl.processVideo(Dialog.this,vidData);
                finish();
    }

      public void downloadFB(String vid_url, String vid_id) {
        try {
            String mBaseFolderPath = android.os.Environment
                    .getExternalStorageDirectory()
                    + File.separator
                    + "Facebook Video Downloader" + File.separator;
            if (!new File(mBaseFolderPath).exists()) {
                new File(mBaseFolderPath).mkdir();
            }
            String mFilePath = "file://" + mBaseFolderPath + "/" + vid_id
                    + ".mp4";
            Uri downloadUri = Uri.parse(vid_url);
            DownloadManager.Request req = new DownloadManager.Request(
                    downloadUri);
            req.setDestinationUri(Uri.parse(mFilePath));
            req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            DownloadManager dm = (DownloadManager) getApplicationContext()
                    .getSystemService(getApplicationContext().DOWNLOAD_SERVICE);
            dm.enqueue(req);
            Toast toast = Toast.makeText(getApplicationContext(), "Download Started",
                                         Toast.LENGTH_SHORT);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(), "Download Failed",
                                         Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}