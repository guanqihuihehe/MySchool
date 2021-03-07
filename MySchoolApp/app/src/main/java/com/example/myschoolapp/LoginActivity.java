package com.example.myschoolapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.example.myschoolapp.Constant.current_usercollege;
import static com.example.myschoolapp.Constant.current_usergrade;
import static com.example.myschoolapp.Constant.current_username;
import static com.example.myschoolapp.Constant.current_usernickname;
import static com.example.myschoolapp.Constant.current_userpassword;
import static com.example.myschoolapp.Constant.current_userstu_no;
import static com.example.myschoolapp.Constant.icon_path;
import static com.example.myschoolapp.Constant.user_file;

public class LoginActivity extends BaseActivity {

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private EditText accountEdit;

    private EditText passwordEdit;

    private Button login;

    private CheckBox rememberPass;

    private MyDatabaseHelper dbHelper;


    public TextView jumpToRegister;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final LinearLayout linearLayout=findViewById(R.id.bg_login);
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        jumpToRegister=findViewById(R.id.jumpToRegister);

        dbHelper = new MyDatabaseHelper(this, "user.db", null, 2);
        SharedPreferences current_user=this.getSharedPreferences("current_user",MODE_MULTI_PROCESS);
        String already_login_user=current_user.getString("name","");
        String already_login_password=current_user.getString("password","");

//        init();

        if(already_login_user!=null&&!already_login_user.equals("")){
            current_username=already_login_user;
            current_userpassword=already_login_password;
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
//        boolean isRemember = pref.getBoolean("remember_password", false);
//        if (isRemember) {
////         将账号和密码都设置到文本框中
//            String account = pref.getString("account", "");
//            String password = pref.getString("password", "");
//            accountEdit.setText(account);
//            passwordEdit.setText(password);
//            rememberPass.setChecked(true);
//        }
//        accountEdit.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String account = accountEdit.getText().toString();
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//                int flag=0;
//                Cursor cursor = db.query("User", null, null, null, null, null, null);
//                if (cursor.moveToFirst()) {
//                    do {
//                        // 遍历Cursor对象，取出数据并打印
//                        String check_name = cursor.getString(cursor.getColumnIndex("name"));
//                        String check_password = cursor.getString(cursor.getColumnIndex("password"));
//                        Log.d("MainActivity", "check name " + check_name);
//                        Log.d("MainActivity", "check password " + check_password);
//                        if(account.equals(check_name) )
//                        {
//                            passwordEdit.setText(check_password);
//                            flag=1;
//                            break;
//                        }
//                    } while (cursor.moveToNext());
//                }
//                cursor.close();
//                if(flag==0)
//                {
//                    passwordEdit.setText("");
//                }
//
//            }
//        });

        jumpToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                int flag1=0,flag2=0;
                db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("User", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        // 遍历Cursor对象，取出数据并打印
                        String check_name = cursor.getString(cursor.getColumnIndex("name"));
                        String check_password = cursor.getString(cursor.getColumnIndex("password"));
                        Log.d("MainActivity", "name is " + check_name);
                        Log.d("MainActivity", "password is " + check_password);
                        if(check_name.equals(account))
                        {
                            flag1=1;
                            if(check_password.equals(password))
                            {
                                flag2=1;
                                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                current_username=account;
                                current_userpassword=password;
                                SharedPreferences current_user=getSharedPreferences("current_user",  MODE_MULTI_PROCESS);
                                SharedPreferences.Editor  editor = current_user.edit();
                                editor.clear();
                                editor.putString("name",account);
                                editor.putString("password",password);
                                editor.apply();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                            }
                            break;
                        }
                    } while (cursor.moveToNext());
                }
                cursor.close();
                if(flag1==0)
                {
                    Toast.makeText(LoginActivity.this, "此账号尚未注册", Toast.LENGTH_SHORT).show();
                }
                else if(flag2==0)
                {
                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                }



                // 如果账号是admin且密码是123456，就认为登录成功
//                if (account.equals("admin") && password.equals("123456")) {
//                    editor = pref.edit();
//                    if (rememberPass.isChecked()) { // 检查复选框是否被选中
//                        editor.putBoolean("remember_password", true);
//                        editor.putString("account", account);
//                        editor.putString("password", password);
//                    } else {
//                        editor.clear();
//                    }
//                    editor.apply();
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(LoginActivity.this, "account or password is invalid",
//                            Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }




}

