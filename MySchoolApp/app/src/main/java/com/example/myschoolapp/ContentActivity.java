package com.example.myschoolapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ContentActivity extends BaseActivity {
    private WebView webView;
    private ProgressBar progress;
    private EditText editText;
    private Button click;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        String url = getIntent().getStringExtra("url");
        String position = getIntent().getStringExtra("position");
        String title = getIntent().getStringExtra("title");
        System.out.println(title+" "+url+" "+position);
        context=this;
        initView();
        initData();
        initListener();
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

    }


    private void initData() {
//        WebSettings settings = webView.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setUseWideViewPort(true);//???????????????????????????????????????
//        settings.setLoadWithOverviewMode(true);
//        // ?????????????????????
//        settings.setBuiltInZoomControls(true);
//        settings.setSupportZoom(true);
//        //????????????????????????
//        settings.setLoadsImagesAutomatically(true);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);// ??????????????????
//        // ????????????
//        settings.setDisplayZoomControls(false);

        webView.setWebViewClient(new WebViewClient(){

            // ???????????????????????????
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            // ??????????????????
            @Override
            public void onPageFinished(WebView view, String url) {
                System.out.println("????????????");
                super.onPageFinished(view, url);
                if(progress!=null){
                    progress.setVisibility(View.GONE);
                }
            }
        });

    }

    private void initView() {
        progress = (ProgressBar) findViewById(R.id.progress);
        webView = (WebView) findViewById(R.id.web_view);
//        editText = (EditText) findViewById(R.id.url);
//        click = (Button) findViewById(R.id.click);
    }

    private void initListener() {
//         ????????????????????????
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progress.setVisibility(View.VISIBLE);
                progress.setProgress(newProgress);
            }
        });

//        click.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("???????????????",editText.getText().toString().trim());
//                webView.loadUrl(editText.getText().toString().trim());
//            }
//        });

        // ??????????????????
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                System.out.println("?????????");
                final WebView.HitTestResult hitTestResult = webView.getHitTestResult();
                int type=hitTestResult.getType();
                switch (type){
                    case WebView.HitTestResult.EDIT_TEXT_TYPE: // ?????????????????????
                        break;
                    case WebView.HitTestResult.PHONE_TYPE: // ????????????
                        break;
                    case WebView.HitTestResult.EMAIL_TYPE: // ??????Email
                        break;
                    case WebView.HitTestResult.GEO_TYPE: // ???????????????
                        break;
                    case WebView.HitTestResult.SRC_ANCHOR_TYPE: // ?????????
                        break;
                    case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE: // ???????????????????????????
                    case WebView.HitTestResult.IMAGE_TYPE: // ??????????????????????????????
                        // ??????????????????????????????
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("??????");
                        builder.setMessage("?????????????????????");
                        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String url = hitTestResult.getExtra();
                                // ?????????????????????
                                DownPicUtil.downPic(url, new DownPicUtil.DownFinishListener(){

                                    @Override
                                    public void getDownPath(String s) {
                                        Toast.makeText(context,"????????????",Toast.LENGTH_LONG).show();
                                        Message msg = Message.obtain();
                                        msg.obj=s;
                                        handler.sendMessage(msg);
                                    }
                                });

                            }
                        });
                        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                            // ??????dismiss
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });

                        AlertDialog dialog = builder.create();

                        dialog.show();
                        Button button1=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        button1.setTextColor(Color.parseColor("#34ace0"));
                        Button button2=dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                        button2.setTextColor(Color.parseColor("#333333"));
                        return true;
                    case WebView.HitTestResult.UNKNOWN_TYPE: //??????
                        break;
                }
//                // ?????????????????????????????????????????????????????????
//                if(hitTestResult.getType()== WebView.HitTestResult.IMAGE_TYPE||
//                        hitTestResult.getType()== WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE){
//
//
//                }
                return false;
            }
        });

//        webView.loadUrl("http://www.baidu.com");
    }

    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String picFile = (String) msg.obj;
            String[] split = picFile.split("/");
            String fileName = split[split.length-1];
//            try {
//                MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), picFile, fileName, null);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
            // ????????????????????????
            getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + picFile)));
            Toast.makeText(context,"??????????????????:"+picFile,Toast.LENGTH_LONG).show();
        }
    };


    // ???????????????????????????????????????
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            if(webView != null){
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }



}
