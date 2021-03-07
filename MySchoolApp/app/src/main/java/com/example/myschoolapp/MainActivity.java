package com.example.myschoolapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.File;

import static com.example.myschoolapp.Constant.current_usercollege;
import static com.example.myschoolapp.Constant.current_usergrade;
import static com.example.myschoolapp.Constant.current_username;
import static com.example.myschoolapp.Constant.current_usernickname;
import static com.example.myschoolapp.Constant.current_userpassword;
import static com.example.myschoolapp.Constant.current_userstu_no;
import static com.example.myschoolapp.Constant.fragment_index;
import static com.example.myschoolapp.Constant.icon_file;
import static com.example.myschoolapp.Constant.icon_path;
import static com.example.myschoolapp.Constant.maincontext;
import static com.example.myschoolapp.Constant.user_file;
import static com.example.myschoolapp.Constant.user_path;

public class MainActivity extends BaseActivity {

    private MyDatabaseHelper dbHelper;
    public SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        maincontext=this;
        if	(ContextCompat.checkSelfPermission (MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED   )	{
            ActivityCompat.requestPermissions(MainActivity.this,	new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE	},	1);
        }
        if(ContextCompat.checkSelfPermission (MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED   )	{
            ActivityCompat.requestPermissions(MainActivity.this,	new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE	},	1);
        }
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        SharedPreferences current_user=getSharedPreferences("current_user", MODE_MULTI_PROCESS);

        dbHelper=new MyDatabaseHelper(this, "user.db", null, 2);
        db = dbHelper.getWritableDatabase();
        init();

    }

    public void init(){
        db=dbHelper.getWritableDatabase();
        Cursor cursor = db.query("User", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                String check_name = cursor.getString(cursor.getColumnIndex("name"));
                String check_password = cursor.getString(cursor.getColumnIndex("password"));
                String check_college = cursor.getString(cursor.getColumnIndex("college"));
                String check_grade = cursor.getString(cursor.getColumnIndex("grade"));
                String check_stu_no = cursor.getString(cursor.getColumnIndex("stu_no"));
//                String check_password = cursor.getString(cursor.getColumnIndex("person_path"));
                String check_nickname = cursor.getString(cursor.getColumnIndex("nickname"));
//
//                Log.d("MainActivity", "name is " + check_name);
//                Log.d("MainActivity", "password is " + check_password);
                if(check_name.equals(current_username))
                {
                    current_usercollege=check_college;
                    current_usergrade=check_grade;
                    current_usernickname=check_nickname;
                    current_userstu_no=check_stu_no;
                    System.out.println("用户信息："+current_username+" "+current_usergrade+" "+current_usercollege+" "+current_usernickname+" "+current_userstu_no);
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        user_file=current_username;
        String userpath=getUserPath();
        System.out.println("初始化Icon路径："+icon_path);
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("重新加载主活动");
        if(fragment_index==3){
            fragment_index=0;
            refreshdata();
        }
//        refreshdata();

    }

    public void refreshdata(){
        ImageView imageView=findViewById(R.id.myicon);
        TextView nicknameview=findViewById(R.id.mynickname);
        TextView college_grade=findViewById(R.id.mycollege_grade);
        if(icon_file!=null){
            File tempfile= new File(icon_file);
            if(tempfile.list()!=null){
                if(tempfile.list().length!=0){
                    Bitmap bitmap = BitmapFactory.decodeFile(icon_path);
                    Bitmap bitmap1= BitmapUtil.toRoundBitmap(bitmap);
                    imageView.setImageBitmap(bitmap1);
//                tv_head.setText("");
                }
                else{
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getResources().getDrawable(R.drawable.smile);
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    imageView.setImageBitmap(BitmapUtil.toRoundBitmap(bitmap));
                }
            }
            else {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getResources().getDrawable(R.drawable.smile);
                Bitmap bitmap = bitmapDrawable.getBitmap();
                imageView.setImageBitmap(BitmapUtil.toRoundBitmap(bitmap));
            }
        }
        else{
            BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getResources().getDrawable(R.drawable.smile);
            Bitmap bitmap = bitmapDrawable.getBitmap();
            imageView.setImageBitmap(BitmapUtil.toRoundBitmap(bitmap));
        }

        if(current_usernickname!=null){
            nicknameview.setText(current_usernickname);
        }
        if(current_usercollege!=null&&current_usergrade!=null){
            String res=current_usercollege+"  "+current_usergrade;
            college_grade.setText(res);
        }
    }


    public static String getUserPath(){
        // 获取存储卡的目录
        String appExternalDir;
        String userp;
        String iconfile;
        if (Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED)){
            appExternalDir =Environment.getExternalStorageDirectory()+ File.separator+"MySchool";
            System.out.println("appExternalDir=="+appExternalDir);
            System.out.println("新建情况："+(new File(appExternalDir)).mkdirs());
            userp =appExternalDir+ File.separator+user_file;
            System.out.println("userp=="+userp);
            (new File(userp)).mkdirs();
            iconfile=userp+File.separator+"MyIcon";
            (new File(iconfile)).mkdirs();
        }
        else{
            System.out.println("the device has not got a external storage");
//            Log.i(TAG,"the device has not got a external storage");
            appExternalDir =null;
            iconfile=null;
            userp=null;
        }

        String iconpath=iconfile+File.separator+"myicon.png";
        user_path=userp;
        icon_file=iconfile;
        icon_path=iconpath;
        System.out.println("用户文件夹："+user_path);
        System.out.println("图标文件夹："+icon_file);
        System.out.println("图标路径："+icon_path);
        return appExternalDir;
    }

    public void test(){

    }
}

