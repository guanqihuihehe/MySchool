package com.example.myschoolapp.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myschoolapp.BaseActivity;
import com.example.myschoolapp.R;

public class DialingActivity extends BaseActivity {

    TextView textView1,textView2,textView3,textView4;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialing);
        if	(ContextCompat.checkSelfPermission (this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED   )	{
            ActivityCompat.requestPermissions(this,	new String[]{
                    Manifest.permission.CALL_PHONE	},	1);
        }
        imageView=findViewById(R.id.backToMain);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        textView1=findViewById(R.id.phone1);
        textView2=findViewById(R.id.phone2);
        textView3=findViewById(R.id.phone3);
        textView4=findViewById(R.id.phone4);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pno=textView1.getText().toString();
                Intent callingIntent=new Intent();
                callingIntent.setAction(Intent.ACTION_CALL);
                callingIntent.setData( Uri.parse("tel:" + pno));
                startActivity(callingIntent);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pno=textView2.getText().toString();
                Intent callingIntent=new Intent();
                callingIntent.setAction(Intent.ACTION_CALL);
                callingIntent.setData( Uri.parse("tel:" + pno));
                startActivity(callingIntent);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pno=textView3.getText().toString();
                Intent callingIntent=new Intent();
                callingIntent.setAction(Intent.ACTION_CALL);
                callingIntent.setData( Uri.parse("tel:" + pno));
                startActivity(callingIntent);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pno=textView4.getText().toString();
                Intent callingIntent=new Intent();
                callingIntent.setAction(Intent.ACTION_CALL);
                callingIntent.setData( Uri.parse("tel:" + pno));
                startActivity(callingIntent);
            }
        });
    }
}
