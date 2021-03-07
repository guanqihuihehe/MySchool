package com.example.myschoolapp.ui.mine;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myschoolapp.BaseActivity;
import com.example.myschoolapp.MyDatabaseHelper;
import com.example.myschoolapp.R;

import static com.example.myschoolapp.Constant.current_usercollege;
import static com.example.myschoolapp.Constant.current_usergrade;
import static com.example.myschoolapp.Constant.current_username;
import static com.example.myschoolapp.Constant.current_usernickname;
import static com.example.myschoolapp.Constant.current_userpassword;
import static com.example.myschoolapp.Constant.current_userstu_no;

public class EditMyDataActivity extends BaseActivity {

    public ImageView goback;
    public EditText edit_content;
    public TextView change_title;
    public Button button_confirm,button_cancle;
    private MyDatabaseHelper dbHelper;
    public SQLiteDatabase db;
    int type;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmydata);
        goback=findViewById(R.id.goback);
        edit_content=findViewById(R.id.edit_content);
        button_confirm=findViewById(R.id.make_confirm);
        button_cancle=findViewById(R.id.make_cancle);
        change_title=findViewById(R.id.change_title);
        dbHelper=new MyDatabaseHelper(this, "user.db", null, 2);
        db = dbHelper.getWritableDatabase();
        //获取从上个页面传来的类型并显示本页
        Intent intent = getIntent();
        type = intent.getIntExtra("type",0);
        switch (type){
            case 1:
                change_title.setText("修改昵称");
                break;
            case 2:
                change_title.setText("修改密码");
                break;
            case 3:
                change_title.setText("修改学院");
                break;
            case 4:
                change_title.setText("修改年级");
                break;
            case 5:
                change_title.setText("修改学号");
                break;
        }

        button_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content= edit_content.getText().toString();
                ContentValues values = new ContentValues();
                //修改条件
                String whereClause = "name=?";
                //修改添加参数
                String[] whereArgs={current_username};
                if(!content.equals("")){
                    switch (type){
                        case 1:
                            values.put("nickname",content);
                            //修改
                            db.update("User",values,whereClause,whereArgs);
                            current_usernickname=content;
                            System.out.println("content:"+content);
                            break;
                        case 2:
                            values.put("password",content);
                            //修改
                            db.update("User",values,whereClause,whereArgs);
                            current_userpassword=content;
                            System.out.println("content:"+content);
                            break;
                        case 3:
                            values.put("college",content);
                            //修改
                            db.update("User",values,whereClause,whereArgs);
                            current_usercollege=content;
                            System.out.println("content:"+content);
                            break;
                        case 4:
                            values.put("grade",content);
                            //修改
                            db.update("User",values,whereClause,whereArgs);
                            current_usergrade=content;
                            System.out.println("content:"+content);
                            break;
                        case 5:
                            values.put("stu_no",content);
                            //修改
                            db.update("User",values,whereClause,whereArgs);
                            current_userstu_no=content;
                            System.out.println("content:"+content);
                            break;

                    }
                }
                else {
                    Toast.makeText(EditMyDataActivity.this, "修改值不能为空", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });
    }

}
