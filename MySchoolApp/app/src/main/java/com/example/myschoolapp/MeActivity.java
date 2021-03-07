package com.example.myschoolapp;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.myschoolapp.ui.mine.ChangeMyInfoActivity;
import com.example.myschoolapp.ui.mine.EditMyDataActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.example.myschoolapp.Constant.current_usercollege;
import static com.example.myschoolapp.Constant.current_usergrade;
import static com.example.myschoolapp.Constant.current_usernickname;
import static com.example.myschoolapp.Constant.current_userpassword;
import static com.example.myschoolapp.Constant.current_userstu_no;
import static com.example.myschoolapp.Constant.fragment_index;
import static com.example.myschoolapp.Constant.icon_file;
import static com.example.myschoolapp.Constant.icon_path;
import static com.example.myschoolapp.Constant.maincontext;

/**
 * 个人信息
 */
public class MeActivity extends BaseActivity implements View.OnClickListener {

    public ImageView iv_back, iv_head;
    public RelativeLayout rl_head;
    public LinearLayout rl_name,rl_password,rl_college,rl_grade,rl_stuno;
    private int SELECT_PICTURE = 0x00;
    private int SELECT_CAMER = 0x01;
    private Bitmap bitmap;
//    private RoundTools roundTools;
    private Dialog bottomDialog;
    private TextView tv_head, tv_name,tv_grade,tv_college,tv_stu_no,tv_password;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        initView();
        //获取从上个页面传来的昵称并显示本页
//        Intent intent = getIntent();
//        String name = intent.getStringExtra("name");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("重新打开");
//        iv_head.setImageBitmap(BitmapUtil.toRoundBitmap(photo));
        refreshdata();

    }

    public void refreshdata(){
        if(icon_file!=null){
            File tempfile= new File(icon_file);
            if(tempfile.list().length!=0){
                Bitmap bitmap = BitmapFactory.decodeFile(icon_path);
                Bitmap bitmap1=BitmapUtil.toRoundBitmap(bitmap);
                iv_head.setImageBitmap(bitmap1);
//                tv_head.setText("");
            }
            else{
                BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getResources().getDrawable(R.drawable.smile);
                Bitmap bitmap = bitmapDrawable.getBitmap();
                iv_head.setImageBitmap(BitmapUtil.toRoundBitmap(bitmap));
            }
        }
        else{
            BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getResources().getDrawable(R.drawable.smile);
            Bitmap bitmap = bitmapDrawable.getBitmap();
            iv_head.setImageBitmap(BitmapUtil.toRoundBitmap(bitmap));
        }

        if(current_usernickname!=null){
            tv_name.setText(current_usernickname);
        }
        if(current_usercollege!=null){
            tv_college.setText(current_usercollege);
        }
        if(current_usergrade!=null){
            tv_grade.setText(current_usergrade);
        }
        if(current_userstu_no!=null){
            tv_stu_no.setText(current_userstu_no);
        }
//        if(current_userpassword!=null){
//            tv_password.setText(current_userpassword);
//        }
    }

    public int getLayoutID() {
        return R.layout.activity_me;
    }

    public void initListener() {

    }

    public void initView() {
        iv_back = findViewById(R.id.iv_back);
        rl_name = findViewById(R.id.rl_name);
        rl_head = findViewById(R.id.rl_head);
        rl_grade = findViewById(R.id.rl_grade);
        rl_college = findViewById(R.id.rl_college);
        rl_stuno = findViewById(R.id.rl_stu_no);
        rl_password = findViewById(R.id.rl_password);
        iv_head = findViewById(R.id.iv_head);
        tv_name = findViewById(R.id.tv_name);
        tv_grade = findViewById(R.id.tv_grade);
        tv_college = findViewById(R.id.tv_college);
        tv_password = findViewById(R.id.tv_password);
        tv_stu_no = findViewById(R.id.tv_stu_no);
        tv_head=findViewById(R.id.tv_head);

        iv_back.setOnClickListener(this);
        rl_name.setOnClickListener(this);
        rl_head.setOnClickListener(this);
        rl_password.setOnClickListener(this);
        rl_college.setOnClickListener(this);
        rl_grade.setOnClickListener(this);
        rl_stuno.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        Intent jumpintent;
        switch (view.getId()) {
            case R.id.iv_back:
                fragment_index=3;
                finish();
                break;
            case R.id.rl_head:
                jumpintent = new Intent(MeActivity.this, ChangeMyInfoActivity.class);
                startActivity(jumpintent);
                break;
            case R.id.rl_name:
                jumpintent = new Intent(this, EditMyDataActivity.class);
                jumpintent.putExtra("type",1);
                startActivity(jumpintent);
                break;
            case R.id.rl_password:
                jumpintent = new Intent(MeActivity.this, EditMyDataActivity.class);
                jumpintent.putExtra("type",2);
                startActivity(jumpintent);
                break;
            case R.id.rl_college:
                jumpintent = new Intent(MeActivity.this, EditMyDataActivity.class);
                jumpintent.putExtra("type",3);
                startActivity(jumpintent);
                break;
            case R.id.rl_grade:
                jumpintent = new Intent(MeActivity.this, EditMyDataActivity.class);
                jumpintent.putExtra("type",4);
                startActivity(jumpintent);
                break;
            case R.id.rl_stu_no:
                jumpintent = new Intent(MeActivity.this, EditMyDataActivity.class);
                jumpintent.putExtra("type",5);
                startActivity(jumpintent);
                break;
//                //选择对话框
//                bottomDialog = new Dialog(this, R.style.BottomDialog);
//                View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_normal, null);
//                //获取Dialog的监听
//                TextView tv_camera = (TextView) contentView.findViewById(R.id.tv_camera);
//                TextView tv_chose = (TextView) contentView.findViewById(R.id.tv_chose);
//                TextView tv_cancle = (TextView) contentView.findViewById(R.id.tv_cancle);
//                tv_camera.setOnClickListener(this);
//                tv_chose.setOnClickListener(this);
//                tv_cancle.setOnClickListener(this);
//
//                bottomDialog.setContentView(contentView);
//                ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
//                layoutParams.width = getResources().getDisplayMetrics().widthPixels;
//                contentView.setLayoutParams(layoutParams);
//                bottomDialog.getWindow().setGravity(Gravity.BOTTOM);//弹窗位置
//                bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);//弹窗样式
//                bottomDialog.show();//显示弹窗
//                break;
//            case R.id.tv_camera://自定义Doalog的点击事件
//                //通过相机拍摄照片
//                Intent intentCamer = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intentCamer, SELECT_CAMER);
//                bottomDialog.dismiss();//取消弹窗
//                //设置头像后提示字体不显示
//                tv_tip.setText("");
//                break;
//            case R.id.tv_chose:
//                //通过相册选择图片
//                intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.addCategory(intent.CATEGORY_OPENABLE);
//                intent.setType("image/*");
//                startActivityForResult(intent.createChooser(intent, "选择图片"), SELECT_PICTURE);
//                bottomDialog.dismiss();//取消弹窗
//                //设置头像后提示字体不显示
//                tv_tip.setText("");
//                break;
//            case R.id.tv_cancle:
//                bottomDialog.dismiss(); //取消弹窗
//                break;
            default:
                break;
        }
    }

//    /**
//     * 返回结果处理
//     *
//     * @param requestCode 请求代码
//     * @param resultCode  结果代码
//     * @param data        返回数据
//     */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == SELECT_PICTURE) {
//            handle(resultCode, data);
//            Bundle extras = data.getExtras();
//            Bitmap photo = extras.getParcelable("data");
//            iv_head.setImageBitmap(BitmapUtil.toRoundBitmap(photo));
////            roundTools = new RoundTools();
////            Bitmap roundAlbum = roundTools.toRoundBitmap(bitmap);
////            iv_head.setImageBitmap(roundAlbum);
//        } else if (requestCode == SELECT_CAMER) {
//            if (data.getData() == null) {
//                bitmap = (Bitmap) data.getExtras().get("data");
//                Bundle extras = data.getExtras();
//                Bitmap photo = extras.getParcelable("data");
//                iv_head.setImageBitmap(BitmapUtil.toRoundBitmap(photo));
////                Log.i(TAG, "BitData    " + bitmap);
////                roundTools = new RoundTools();
////                Bitmap roundCamer = roundTools.toRoundBitmap(bitmap);
////                iv_head.setImageBitmap(roundCamer);
//            } else try {
//                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
//                if (bitmap != bitmap) {//主要是防止handle处理出错，就会将先前获取相册的照片show出来
//                    iv_head.setImageBitmap(bitmap);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 数据处理 共同点提取
//     *
//     * @param resultCode
//     * @param data
//     */
//    private void handle(int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {//结果代码是Ok的
//            Uri uri = data.getData();
//            if (uri != null && data.getData() != null) {
//                ContentResolver contentResolver = this.getContentResolver();
//                if (bitmap != null) {
//                    bitmap.recycle();
//                }
//                try {
//                    bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri));//出错
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            } else {
////                Log.i(TAG, "uri为空或者data为空   " + "数据：" + data.getData() + "  uri: " + uri);
//            }
//        }
//    }

    @Override
    public void onBackPressed() {
        fragment_index=3;
        finish();
    }
}