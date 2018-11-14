package com.example.admin.facebookdownloader;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.downloader.Status;
import com.downloader.OnStartOrResumeListener;

import java.io.File;
import java.util.Locale;
import java.util.Random;

public class Download extends AppCompatActivity {
    private static String dirPath;
//    String URL1 ="http://techslides.com/demos/sample-videos/small.mp4";

    String URL1,URL2;
    int value;
    String p;
    Button buttonOne,buttonCancelOne,buttonTwo,buttonCancelTwo;
    TextView textViewProgressOne,textone,textViewProgressTwo,textTwo;
    ProgressBar progressBarOne, progressBarTwo;
    int downloadIdOne,downloadIdTwo;
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotificationManager;
    LinearLayout R1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        dirPath = Environment.getExternalStorageDirectory() + File.separator
                + "Facebook Video Downloader" + File.separator;
        if (!new File(dirPath).exists()) {
            new File(dirPath).mkdir();
        }

        try {
            if (StaticData.URL1.isEmpty()) {
                Intent Intent = new Intent(getApplicationContext(), MainActivity1.class);
                startActivity(Intent);
            }
            else
            {
                init();

                //Intent i= getIntent();
                //URL1=i.getStringExtra("URL");
                URL1=StaticData.URL1;
                onClickListenerOne();
                buttonOne.performClick();
                StaticData.URL1=null;

//                URL2=StaticData.URL;
//                onClickListenerTwo();
//                buttonTwo.performClick();
//                StaticData.URL=null;
            }
        }
        catch (Exception e) {

            Log.d("HowException", e.toString());
        }



    }
    private void init() {
        R1= findViewById(R.id.R1);
        buttonOne = findViewById(R.id.buttonOne);
        buttonCancelOne = findViewById(R.id.buttonCancelOne);
        textViewProgressOne = findViewById(R.id.textViewProgressOne);
        textone=findViewById(R.id.textone);
        progressBarOne = findViewById(R.id.progressBarOne);

        buttonTwo = findViewById(R.id.buttonTwo);
        buttonCancelTwo = findViewById(R.id.buttonCancelTwo);
        textViewProgressTwo = findViewById(R.id.textViewProgressTwo);
        textTwo=findViewById(R.id.textTwo);
        progressBarTwo = findViewById(R.id.progressBarTwo);
        mBuilder=new NotificationCompat.Builder(getApplicationContext());
        mNotificationManager=  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }
    public void onClickListenerOne() {
        Toast.makeText(getApplicationContext(), "Download Started", Toast.LENGTH_SHORT).show();
      // Intent in =new Intent(getApplicationContext(),OtherFB.class);
       //startActivity(in);
        Random rand = new Random();
        value = rand.nextInt(10000);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mBuilder.setSmallIcon(R.drawable.download)
                        .setContentTitle("Video" + value + ".mp4");
                mNotificationManager.notify(1, mBuilder.build());
                textone.setText("Video" + value + ".mp4");
                if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne)) {
                    PRDownloader.pause(downloadIdOne);
                    return;
                }

                buttonOne.setEnabled(false);
                progressBarOne.setIndeterminate(true);
                mBuilder.setProgress(100,0,false);
                progressBarOne.getIndeterminateDrawable().setColorFilter(
                        Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);

                if (Status.PAUSED == PRDownloader.getStatus(downloadIdOne)) {
                    PRDownloader.resume(downloadIdOne);
                    return;
                }
                 p="Video" + value + ".mp4";
               // Toast.makeText(getApplicationContext(),p,Toast.LENGTH_SHORT).show();
                downloadIdOne = PRDownloader.download(URL1, dirPath, p)
                        .build()
                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                            @Override
                            public void onStartOrResume() {
                                progressBarOne.setIndeterminate(false);
                              //  mBuilder.setProgress(100,0,false);
                                buttonOne.setEnabled(true);
                                buttonOne.setText("Pause");
                                buttonCancelOne.setEnabled(true);
                            }
                        })
                        .setOnPauseListener(new OnPauseListener() {
                            @Override
                            public void onPause() {
                                buttonOne.setText("Resume");
                            }
                        })
                        .setOnCancelListener(new OnCancelListener() {
                            @Override
                            public void onCancel() {
                                buttonOne.setText("Start");
                                buttonCancelOne.setEnabled(false);
                                progressBarOne.setProgress(0);
                                textViewProgressOne.setText("");
                                downloadIdOne = 0;
                                progressBarOne.setIndeterminate(false);
                              //  mBuilder.setProgress(100,0,false);

                            }
                        })
                        .setOnProgressListener(new OnProgressListener() {
                            @Override
                            public void onProgress(Progress progress) {
                                long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                progressBarOne.setProgress((int) progressPercent);
                                mBuilder.setProgress((int)progress.totalBytes,(int)progress.currentBytes,false);
                                textViewProgressOne.setText(getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                                progressBarOne.setIndeterminate(false);
                                }
                        })
                        .start(new OnDownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                buttonOne.setEnabled(false);
                                buttonCancelOne.setEnabled(false);
                                buttonOne.setText("Completed");
                                R1.setVisibility(View.GONE);
                                finish();
                            }


                            @Override
                            public void onError(Error error) {
                                buttonOne.setText("Start");
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                textViewProgressOne.setText("");
                                progressBarOne.setProgress(0);
                                downloadIdOne = 0;
                                buttonCancelOne.setEnabled(false);
                                progressBarOne.setIndeterminate(false);
                                mBuilder.setProgress(100,0,false);
                                buttonOne.setEnabled(true);
                            }


                        });
            }
        });

        buttonCancelOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNotificationManager.cancel(1);
                PRDownloader.cancel(downloadIdOne);
                textViewProgressOne.setVisibility(View.GONE);
                buttonCancelOne.setVisibility(View.GONE);
                progressBarOne.setVisibility(View.GONE);
                buttonOne.setVisibility(View.GONE);
                textone.setVisibility(View.GONE);

            }
        });
    }


    public void onClickListenerTwo() {

        buttonTwo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mBuilder.setSmallIcon(R.drawable.download)
                        .setContentTitle("Downloading...")
                        .setContentText("Video" + (new Random(10000)).nextInt() + ".mp4");
                mNotificationManager.notify(2, mBuilder.build());
                textTwo.setText("Video" +value+ ".mp4");
                if (Status.RUNNING == PRDownloader.getStatus(downloadIdTwo)) {
                    PRDownloader.pause(downloadIdTwo);
                    return;
                }

                buttonTwo.setEnabled(false);
                progressBarTwo.setIndeterminate(true);
                mBuilder.setProgress(100,0,false);
                progressBarTwo.getIndeterminateDrawable().setColorFilter(
                        Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);

                if (Status.PAUSED == PRDownloader.getStatus(downloadIdTwo)) {
                    PRDownloader.resume(downloadIdTwo);
                    return;
                }

                downloadIdTwo = PRDownloader.download(URL2, dirPath, "Video" + (new Random(10000)).nextInt() + ".mp4")
                        .build()
                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                            @Override
                            public void onStartOrResume() {
                                progressBarTwo.setIndeterminate(false);
                                mBuilder.setProgress(100,0,false);
                                buttonTwo.setEnabled(true);
                                buttonTwo.setText("Pause");
                                buttonCancelTwo.setEnabled(true);
                            }
                        })
                        .setOnPauseListener(new OnPauseListener() {
                            @Override
                            public void onPause() {
                                buttonTwo.setText("Resume");
                            }
                        })
                        .setOnCancelListener(new OnCancelListener() {
                            @Override
                            public void onCancel() {
                                buttonTwo.setText("Start");
                                buttonCancelTwo.setEnabled(false);
                                progressBarTwo.setProgress(0);
                                textViewProgressTwo.setText("");
                                downloadIdTwo = 0;
                                progressBarTwo.setIndeterminate(false);
                                mBuilder.setProgress(100,0,false);

                            }
                        })
                        .setOnProgressListener(new OnProgressListener() {
                            @Override
                            public void onProgress(Progress progress) {
                                long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                progressBarTwo.setProgress((int) progressPercent);
                                mBuilder.setProgress((int)progress.totalBytes,(int)progress.currentBytes,false);
                                textViewProgressTwo.setText(getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                                progressBarTwo.setIndeterminate(false);
                                mBuilder.setProgress((int)progress.totalBytes,(int)progress.currentBytes,false);
                            }
                        })
                        .start(new OnDownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                buttonTwo.setEnabled(false);
                                buttonCancelTwo.setEnabled(false);
                                buttonTwo.setText("Completed");
                            }

                            @Override
                            public void onError(Error error) {
                                buttonTwo.setText("Start");
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                textViewProgressTwo.setText("");
                                progressBarTwo.setProgress(0);
                                downloadIdTwo = 0;
                                buttonCancelTwo.setEnabled(false);
                                progressBarTwo.setIndeterminate(false);
                                mBuilder.setProgress(100,0,false);
                                buttonTwo.setEnabled(true);
                            }


                        });
            }
        });

        buttonCancelTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNotificationManager.cancel(2);
                PRDownloader.cancel(downloadIdTwo);
                textViewProgressTwo.setVisibility(View.GONE);
                buttonCancelTwo.setVisibility(View.GONE);
                progressBarTwo.setVisibility(View.GONE);
                buttonTwo.setVisibility(View.GONE);
                textTwo.setVisibility(View.GONE);

            }
        });
    }


    public static String getProgressDisplayLine(long currentBytes, long totalBytes) {
        return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
    }

    private static String getBytesToMBString(long bytes){
        return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00));
    }


}
