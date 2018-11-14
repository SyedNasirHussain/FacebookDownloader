package com.example.admin.facebookdownloader;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;



public class StreamVideo extends AppCompatActivity {
    ProgressDialog pDialog;
    VideoView videoview;
    String VideoURL;
    //AdView mAdView;
    private static final String TAG_VIDURL = "video_url";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_video);
      //  mAdView = (AdView) findViewById(R.id.adView);
        //inializeFooter();

       // if (isOnline()) {
        //    showBannerAd();
       // } else {
            // mAdView.setVisibility(View.GONE);
           // mAdView.setVisibility(View.GONE);

       // }

        Intent i = getIntent();
        VideoURL = i.getStringExtra(TAG_VIDURL);
        videoview = findViewById(R.id.streaming_video_laner_rla);
        pDialog = new ProgressDialog(StreamVideo.this);
        pDialog.setTitle("Video Stream");
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();



     	try {
        MediaController mediacontroller = new MediaController(
                StreamVideo.this);
        mediacontroller.setAnchorView(videoview);
        Uri video = Uri.parse(VideoURL);
        videoview.setMediaController(mediacontroller);
        videoview.setVideoURI(video);
        } catch (Exception e) {
        Log.e("Error", e.getMessage());
        e.printStackTrace();
    }
		videoview.requestFocus();
		videoview.setOnPreparedListener(new OnPreparedListener() {
        public void onPrepared(MediaPlayer mp) {
            pDialog.dismiss();
            videoview.start();
        }
    });
}

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

//    private void showBannerAd() {
//
//        //mAdView.setVisibility(View.VISIBLE);
//        AdRequest adRequest = new AdRequest.Builder()
//                .build();
//       // mAdView.loadAd(adRequest);
//
//    }

}


