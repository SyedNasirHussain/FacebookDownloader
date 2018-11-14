package com.example.admin.facebookdownloader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class StartActivity extends Activity {
    //    Button go_back, go_forward, buttonRefresh, buttonClearHistory;
    WebView browser;
    String PageURL, PageTitle;
    String target_url = "https://m.facebook.com/";
    String home_url = "http://www.google.com";
    EditText editText;
    ImageView buttonClear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        init();
        setupUI(findViewById(R.id.start_activity));
    }

    public static void hideSoftKeyboard(StartActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
//        activity.findViewById(R.id.clearable_button_clear).setVisibility(INVISIBLE);
    }

    public void setupUI(final View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(StartActivity.this);
//                    findViewById(R.id.ic_refresh).setVisibility(VISIBLE);
                    return false;
                }

            });

        }

        if ((view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {

//                    v.findViewById(R.id.clearable_button_clear).setVisibility(View.VISIBLE);
                    findViewById(R.id.clearable_button_clear).setVisibility(VISIBLE);
                    showHideClearButton();
//                    findViewById(R.id.ic_refresh).setVisibility(INVISIBLE);

                    return false;
                }

            });

        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }


    @SuppressLint("JavascriptInterface")
    public void init() {


//        go_back = (Button) findViewById(R.id.button_go_back);
//        go_forward = (Button) findViewById(R.id.button_go_forward);
//        buttonRefresh = (Button) findViewById(R.id.button_go_refresh);
//        buttonClearHistory = (Button) findViewById(R.id.button_clearHistory);

        buttonClear = (ImageView) findViewById(R.id.clearable_button_clear);
        browser = (WebView) findViewById(R.id.web_view);
        editText = (EditText) findViewById(R.id.editText_url);

        buttonClear.setVisibility(INVISIBLE);

        clearText();

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener(){

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if(arg1 == EditorInfo.IME_ACTION_GO)
                {
                    if (editText.getText().toString().equals(target_url)) {
                        Intent Intent = new Intent(getApplicationContext(), MainActivity.class);
                        getIntent().putExtra("Url", "None");
                        startActivity(Intent);

                    } else if (editText.getText().toString().equals("facebook")) {
                    Intent Intent = new Intent(getApplicationContext(), MainActivity.class);
                    getIntent().putExtra("Url", "None");
                    startActivity(Intent);

                } else if (editText.getText().toString().equals("https://m.facebook.com")) {
                        Intent Intent = new Intent(getApplicationContext(), MainActivity.class);
                        getIntent().putExtra("Url", "None");
                        startActivity(Intent);

                    }
                    else if (editText.getText().toString().equals("https://www.facebook.com")) {
                        Intent Intent = new Intent(getApplicationContext(), MainActivity.class);
                        getIntent().putExtra("Url", "None");
                        startActivity(Intent);

                    }else if (editText.getText().toString().equals("www.facebook.com")) {
                        Intent Intent = new Intent(getApplicationContext(), MainActivity.class);
                        getIntent().putExtra("Url", "None");
                        startActivity(Intent);

                    }
                    else if (editText.getText().toString().equals("facebook.com")) {
                        Intent Intent = new Intent(getApplicationContext(), MainActivity.class);
                        getIntent().putExtra("Url", "None");
                        startActivity(Intent);

                    }

                    else
                        browser.loadUrl(editText.getText().toString());

                }
                return false;
            }

        });


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        browser.setWebViewClient(new myViewClient());
        browser.getSettings().setJavaScriptEnabled(true);
//        browser.addJavascriptInterface(this, "FBDownloader");
        browser.getSettings().setLoadWithOverviewMode(true);
        browser.getSettings().setUseWideViewPort(true);


        try {
            browser.loadUrl(home_url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String writeExternalStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String readExternalStorage = Manifest.permission.READ_EXTERNAL_STORAGE;
        int hasWriteExternalStoragePermission = ActivityCompat.checkSelfPermission(this, writeExternalStorage);
        int hasReadExternalStoragePermission = ActivityCompat.checkSelfPermission(this, readExternalStorage);
        if (hasWriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED || hasReadExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            getPermission();
        }
    }

    private void getPermission() {
        String[] params = null;
        String writeExternalStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String readExternalStorage = Manifest.permission.READ_EXTERNAL_STORAGE;
        int hasWriteExternalStoragePermission = ActivityCompat.checkSelfPermission(this, writeExternalStorage);
        int hasReadExternalStoragePermission = ActivityCompat.checkSelfPermission(this, readExternalStorage);
        List<String> permissions = new ArrayList<String>();
        if (hasWriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED)
            permissions.add(writeExternalStorage);
        if (hasReadExternalStoragePermission != PackageManager.PERMISSION_GRANTED)
            permissions.add(readExternalStorage);
        if (!permissions.isEmpty()) {
            params = permissions.toArray(new String[permissions.size()]);
        }
        if (params != null && params.length > 0) {
            ActivityCompat.requestPermissions(StartActivity.this,
                                              params,
                                              100);
        } else {
            //startActivity(new Intent(this, FadeEdit.class));
            // finish();
        }
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    public void showConnectivityErrorDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
        builder.setCancelable(true);
        builder.setIcon(null);
        builder.setTitle(null);
        builder.setMessage("YOU MUST ENABLE YOUR DATA CONNECTION (WIFI or 3G)");
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("Settings",
                                  new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog, int which) {

                                          Intent settingPage = new Intent(
                                                  android.provider.Settings.ACTION_SETTINGS);
                                          startActivityForResult(settingPage, 0);
                                          dialog.dismiss();
                                      }
                                  });
        builder.setNegativeButton("Cancel",
                                  new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog, int which) {
                                          dialog.dismiss();
                                      }
                                  });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void onClick_button(View view) {

        switch (view.getId()) {

//            case R.id.button_go_back:
//                if (browser.canGoBack())
//                    browser.goBack();
//                break;
//
//            case R.id.button_go_forward:
//                if (browser.canGoForward())
//                    browser.goForward();
//                break;
//
//            case R.id.button_clearHistory:
//                browser.clearHistory();
//                break;
//
//            case R.id.button_home:
//                try {
//                    browser.loadUrl(home_url);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//
            case R.id.ic_refresh:
                browser.reload();
                break;

//            case R.id.clearable_button_clear:
//                editText.setText("");
//                break;

//            case R.id.button_fab:
//                Snackbar.make(view, "FAB button is working", Snackbar.LENGTH_SHORT)
//                        .setAction("CLOSE", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                            }
//                        })
//                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
//                        .show();
//                break;


        }

    }

    public class myViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            //Getting WebPage URL .
            PageURL = view.getUrl();

            //Getting WebPage TITLE .
            PageTitle = view.getTitle();
            editText.setText(PageURL);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String Url) {

            if (Url.equals(target_url)) {
                Intent Intent = new Intent(getApplicationContext(), MainActivity.class);
                getIntent().putExtra("Url", "None");
                startActivity(Intent);

            } else
                view.loadUrl(Url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }


    }

    @Override
    public void onBackPressed() {
        if (browser.canGoBack()) {
            browser.goBack();
        } else
            finish();
    }

    void clearText()
    {
        buttonClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
// TODO Auto-generated method stub
                editText.setText("");
            }
        });
    }

    void showHideClearButton()
    {

        editText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
// TODO Auto-generated method stub
                if (s.length() > 0)
                    buttonClear.setVisibility(VISIBLE);
                else
                    buttonClear.setVisibility(INVISIBLE);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
// TODO Auto-generated method stub
                buttonClear.setVisibility(VISIBLE);

            }
            @Override
            public void afterTextChanged(Editable s)
            {
// TODO Auto-generated method stub





            }
        });
    }
}
