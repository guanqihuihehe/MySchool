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
//        settings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
//        settings.setLoadWithOverviewMode(true);
//        // 使页面支持缩放
//        settings.setBuiltInZoomControls(true);
//        settings.setSupportZoom(true);
//        //支持自动加载图片
//        settings.setLoadsImagesAutomatically(true);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);// 排版适应屏幕
//        // 缩放按钮
//        settings.setDisplayZoomControls(false);

        webView.setWebViewClient(new WebViewClient(){

            // 页面在当前页面跳转
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            // 页面加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                System.out.println("加载完成");
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
//         网页加载进度显示
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
//                Log.e("输入的网站",editText.getText().toString().trim());
//                webView.loadUrl(editText.getText().toString().trim());
//            }
//        });

        // 长按点击事件
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                System.out.println("长按了");
                final WebView.HitTestResult hitTestResult = webView.getHitTestResult();
                int type=hitTestResult.getType();
                switch (type){
                    case WebView.HitTestResult.EDIT_TEXT_TYPE: // 选中的文字类型
                        break;
                    case WebView.HitTestResult.PHONE_TYPE: // 处理拨号
                        break;
                    case WebView.HitTestResult.EMAIL_TYPE: // 处理Email
                        break;
                    case WebView.HitTestResult.GEO_TYPE: // 　地图类型
                        break;
                    case WebView.HitTestResult.SRC_ANCHOR_TYPE: // 超链接
                        break;
                    case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE: // 带有链接的图片类型
                    case WebView.HitTestResult.IMAGE_TYPE: // 处理长按图片的菜单项
                        // 弹出保存图片的对话框
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("提示");
                        builder.setMessage("保存图片到本地");
                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String url = hitTestResult.getExtra();
                                // 下载图片到本地
                                DownPicUtil.downPic(url, new DownPicUtil.DownFinishListener(){

                                    @Override
                                    public void getDownPath(String s) {
                                        Toast.makeText(context,"下载完成",Toast.LENGTH_LONG).show();
                                        Message msg = Message.obtain();
                                        msg.obj=s;
                                        handler.sendMessage(msg);
                                    }
                                });

                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            // 自动dismiss
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
                    case WebView.HitTestResult.UNKNOWN_TYPE: //未知
                        break;
                }
//                // 如果是图片类型或者是带有图片链接的类型
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
            // 最后通知图库更新
            getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + picFile)));
            Toast.makeText(context,"图片保存路径:"+picFile,Toast.LENGTH_LONG).show();
        }
    };


    // 监听返回键返回网页的上一层
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
