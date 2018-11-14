package com.example.admin.facebookdownloader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


public class Download_previewpix extends AppCompatActivity {
    ImageView image;
    String ImgUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_previewpix);
        image= findViewById(R.id.Imageview);
        Intent i = getIntent();
        ImgUrl = i.getStringExtra("video_url");
        Bitmap myBitmap = BitmapFactory.decodeFile(ImgUrl);
        image.setImageBitmap(myBitmap);
    }
}
