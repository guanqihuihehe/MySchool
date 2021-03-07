package com.example.myschoolapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static com.example.myschoolapp.Constant.fragment_index;

public class RegisterActivity extends BaseActivity {

    private EditText accountEdit;
    private EditText passwordEdit;
    private EditText re_passwordEdit;

    private Button registerButton;

    private MyDatabaseHelper dbHelper;

    public ImageView backToLogin;
    public boolean passwordconfirm=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        accountEdit = (EditText) findViewById(R.id.register_account);
        passwordEdit = (EditText) findViewById(R.id.register_password);
        re_passwordEdit = (EditText) findViewById(R.id.re_password);
        registerButton = (Button) findViewById(R.id.register);
        backToLogin=findViewById(R.id.backToLogin);
        dbHelper = new MyDatabaseHelper(this, "user.db", null, 2);
        re_passwordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                re_passwordEdit.setBackgroundResource(R.drawable.et_underline_selected);
                String password1 = passwordEdit.getText().toString();
                String password2 = re_passwordEdit.getText().toString();
                if(!password1.equals(password2)){
                    re_passwordEdit.setBackgroundResource(R.drawable.et_underline_selected);
                }

            }
        });

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAccount();
            }
        });
    }

    public boolean checkAccount(){

        String account = accountEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        String re_password=re_passwordEdit.getText().toString();

        //检验1 重复密码检验
        if(!re_password.equals(password)){
            Toast.makeText(RegisterActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }

        //检验2 格式检验
        if(!checkName(account)){
            Toast.makeText(RegisterActivity.this, "用户名只能是6到10位，由字母或者数字或者下划线组成", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!checkPwd(password)){
            Toast.makeText(RegisterActivity.this, "密码只能是6到20位，由字母或者数字或者下划线组成", Toast.LENGTH_SHORT).show();
            return false;
        }

        //检验3 数据库检验
        SQLiteDatabase db = dbHelper.getWritableDatabase();
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
                    cursor.close();
                    Toast.makeText(RegisterActivity.this, "用户名： "+account+" 已存在", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        //如果没有错那就创建新用户
        ContentValues values = new ContentValues();
        values.put("name",account);
        values.put("password",password);
        db.insert("User",null,values);
        Toast.makeText(RegisterActivity.this, "成功创建新用户： "+account, Toast.LENGTH_SHORT).show();

        //在这里塞进账户密码信息给intent
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

        return true;
    }

    /**
     * 用户名验证 6到10位，只能是字母或者数字或者下划线
     *  @param name
     *  @return
     */
    public static boolean checkName(String name) {
        String regExp = "^[\\w_]{6,9}$";
        if(name.matches(regExp)) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 密码验证 6到20位，只能是字母或者数字或者下划线
     *  @param pwd
     *  @return
     */
    public static boolean checkPwd(String pwd) {
        String regExp = "^[\\w_]{6,20}$";
        if(pwd.matches(regExp)) {
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
