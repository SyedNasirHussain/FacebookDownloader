package com.example.admin.facebookdownloader;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MainActivity extends Activity {

    private static WebView webView;
    private static final String target_url = "https://m.facebook.com/";

    private MyWebChromeClient mWebChromeClient = null;
    private View mCustomView;
    //    Boolean mShouldPause;
    private RelativeLayout mContentView;
    private FrameLayout mCustomViewContainer;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private static final String TAG_VIDURL = "video_url";
    public String vidData, vidId;
    private EditText myEditText;
    ImageView myButtonClear;
    private String PageURL, PageTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setupUI(findViewById(R.id.main_activity));

    }

    public static void hideSoftKeyboard(MainActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
//        activity.findViewById(R.id.main_activity_clearable_button_clear).setVisibility(INVISIBLE);
    }

    public void setupUI(final View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(MainActivity.this);
//                    findViewById(R.id.ic_refresh).setVisibility(VISIBLE);
                    return false;
                }

            });

        }

        if ((view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {

//                    v.findViewById(R.id.clearable_button_clear).setVisibility(View.VISIBLE);
                    findViewById(R.id.main_activity_clearable_button_clear).setVisibility(VISIBLE);
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

        myButtonClear = (ImageView) findViewById(R.id.main_activity_clearable_button_clear);
        webView = findViewById(R.id.web_view);
        myEditText = (EditText) findViewById(R.id.main_activity_editText_url);

        myButtonClear.setVisibility(INVISIBLE);

        clearText();


        myEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_GO) {
                    if (myEditText.getText().toString().equals(target_url)) {
                        try {
                            webView.loadUrl(target_url);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (myEditText.getText().toString().equals("facebook")) {
                        try {
                            webView.loadUrl(target_url);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (myEditText.getText().toString().equals("https://m.facebook.com")) {
                        try {
                            webView.loadUrl(target_url);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (myEditText.getText().toString().equals("https://www.facebook.com")) {
                        try {
                            webView.loadUrl(target_url);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (myEditText.getText().toString().equals("www.facebook.com")) {
                        try {
                            webView.loadUrl(target_url);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (myEditText.getText().toString().equals("facebook.com")) {
                        try {
                            webView.loadUrl(target_url);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else
                        webView.loadUrl(myEditText.getText().toString());

                }
                return false;
            }

        });
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.addJavascriptInterface(this, "FBDownloader");
        mWebChromeClient = new MyWebChromeClient();
        webView.setWebChromeClient(mWebChromeClient);

        mWebChromeClient = new MyWebChromeClient();


        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //Getting WebPage URL .
                PageURL = view.getUrl();

                //Getting WebPage TITLE .
                PageTitle = view.getTitle();
                myEditText.setText(PageURL);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function() { "
                                        + "var el = document.querySelectorAll('div[data-sigil]');"
                                        + "for(var i=0;i<el.length; i++)"
                                        + "{"
                                        + "var sigil = el[i].dataset.sigil;"
                                        + "if(sigil.indexOf('inlineVideo') > -1){"
                                        + "delete el[i].dataset.sigil;"
                                        + "var jsonData = JSON.parse(el[i].dataset.store);"
                                        + "el[i].setAttribute('onClick', 'FBDownloader.processVideo(\"'+jsonData['src']+'\");');"
                                        + "}" + "}" + "})()");
                Log.e("WEBVIEWFIN", url);


            }

            @Override
            public void onLoadResource(WebView view, String url) {
                webView.loadUrl("javascript:(function prepareVideo() { "
                                        + "var el = document.querySelectorAll('div[data-sigil]');"
                                        + "for(var i=0;i<el.length; i++)"
                                        + "{"
                                        + "var sigil = el[i].dataset.sigil;"
                                        + "if(sigil.indexOf('inlineVideo') > -1){"
                                        + "delete el[i].dataset.sigil;"
                                        + "console.log(i);"
                                        + "var jsonData = JSON.parse(el[i].dataset.store);"
                                        + "el[i].setAttribute('onClick', 'FBDownloader.processVideo(\"'+jsonData['src']+'\",\"'+jsonData['videoID']+'\");');"
                                        + "}" + "}" + "})()");
                webView.loadUrl("javascript:( window.onload=prepareVideo;"
                                        + ")()");
            }


        });


        CookieSyncManager.createInstance(getApplicationContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        CookieSyncManager.getInstance().startSync();

        try {
            webView.loadUrl(target_url);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public class MyWebChromeClient extends WebChromeClient {

        FrameLayout.LayoutParams LayoutParameters = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        @Override
        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mContentView = findViewById(R.id.facebook);
            mContentView.setVisibility(View.GONE);
            mCustomViewContainer = new FrameLayout(MainActivity.this);
            mCustomViewContainer.setLayoutParams(LayoutParameters);
            mCustomViewContainer.setBackgroundResource(android.R.color.black);
            view.setLayoutParams(LayoutParameters);
            mCustomViewContainer.addView(view);
            mCustomView = view;
            mCustomViewCallback = callback;
            mCustomViewContainer.setVisibility(View.VISIBLE);
            setContentView(mCustomViewContainer);

        }

        @Override
        public void onHideCustomView() {
            if (mCustomView == null) {
                return;
            } else {
                // Hide the custom view.
                mCustomView.setVisibility(View.GONE);
                // Remove the custom view from its container.
                mCustomViewContainer.removeView(mCustomView);
                mCustomView = null;
                mCustomViewContainer.setVisibility(View.GONE);
                mCustomViewCallback.onCustomViewHidden();
                // Show the content view.
                mContentView.setVisibility(View.VISIBLE);
                setContentView(mContentView);
            }
        }
    }

    @JavascriptInterface
    public void processVideo(final String vidData, final String vidID) {

        Log.e("WEBVIEWJS", "RUN");
        Log.e("WEBVIEWJS", vidData);
        this.vidData = vidData;
        this.vidId = vidID;
        if (vidData != null && vidID != null) {
            alertTwoButtons();
//            onClick_video();
        }
    }

    public void alertTwoButtons() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Select Options")
                .setMessage("Where Do You want to go?")
                .setPositiveButton("Cancel",
                                   new DialogInterface.OnClickListener() {
                                       public void onClick(DialogInterface dialog, int id) {
                                           dialog.cancel();
                                       }
                                   })
                .setNeutralButton("StreamVideo ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            Intent i = new Intent(getApplicationContext(), StreamVideo.class);
                            i.putExtra(TAG_VIDURL, vidData);
                            startActivity(i);
                            Toast toast = Toast.makeText(getApplicationContext(), "Streaming Started",
                                                         Toast.LENGTH_SHORT);
                            toast.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast toast = Toast.makeText(getApplicationContext(), "Streaming Failed",
                                                         Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Download", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getApplicationContext(), Dialog.class);
                        i.putExtra("vid_data", vidData);
                        i.putExtra("vid_id", vidId);
                        startActivity(i);
                        dialog.cancel();
                    }
                }).show();
    }


//    public void onClick_button(View view) {
//
//        switch (view.getId()) {
//
//            case R.id.button_downloaded:
//                webView.reload();
//                break;
//
//            case R.id.button_downloading:
//
//                break;
//        }
//    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else
            finish();
    }

    public void onClick_button(View view) {
        switch (view.getId()) {

            case R.id.button_downloaded:
                Intent myIntent = new Intent(getApplicationContext(), All_Downloads.class);
                startActivity(myIntent);
                break;

            case R.id.button_downloading:
                Intent intent = new Intent(getApplicationContext(), MainActivity1.class);
                startActivity(intent);
                break;
            case R.id.main_activity_ic_refresh:
                webView.reload();
                break;

            case R.id.main_activity_clearable_button_clear:
                myEditText.setText("");
                break;
        }

    }

    void clearText() {
        myButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, "Clear Button Working", Toast.LENGTH_SHORT);
                myEditText.setText("");
            }
        });
    }

    void showHideClearButton() {

        myEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
// TODO Auto-generated method stub
                if (s.length() > 0)
                    myButtonClear.setVisibility(VISIBLE);
                else
                    myButtonClear.setVisibility(INVISIBLE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
// TODO Auto-generated method stub
                myButtonClear.setVisibility(VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {
// TODO Auto-generated method stub


            }
        });
    }

}
