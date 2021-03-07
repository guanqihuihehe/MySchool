package com.example.myschoolapp.ui.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myschoolapp.BitmapUtil;
import com.example.myschoolapp.ContentActivity;
import com.example.myschoolapp.MeActivity;
import com.example.myschoolapp.R;

import java.io.File;

import static android.content.Context.MODE_MULTI_PROCESS;
import static com.example.myschoolapp.Constant.current_usercollege;
import static com.example.myschoolapp.Constant.current_usergrade;
import static com.example.myschoolapp.Constant.current_usernickname;
import static com.example.myschoolapp.Constant.icon_file;
import static com.example.myschoolapp.Constant.icon_path;
import static com.example.myschoolapp.Constant.maincontext;

public class MineFragment extends Fragment {

    private MineViewModel mineViewModel;

    public LinearLayout change_my_info;

    ImageView imageView;
    TextView nicknameview;
    TextView college_grade;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        System.out.println("重新创建界面");
        mineViewModel =
                ViewModelProviders.of(this).get(MineViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mine, container, false);

        if (root.findViewById(R.id.right_func) == null){//手机模式
            System.out.println("进入手机模式");
            change_my_info=root.findViewById(R.id.change_my_info);
//        final TextView textView = root.findViewById(R.id.text_mine);
            mineViewModel.getText().observe(this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
//                textView.setText(s);
                }
            });

            change_my_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(maincontext, MeActivity.class);
                    maincontext.startActivity(intent);
                }
            });

            final Button offline=root.findViewById(R.id.force_offline);

            offline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent("com.example.myschoolapp.FORCE_OFFLINE");
                    maincontext.sendBroadcast(intent);
                }
            });


            imageView=root.findViewById(R.id.myicon);
            nicknameview=root.findViewById(R.id.mynickname);
            college_grade=root.findViewById(R.id.mycollege_grade);

            refreshdata();
        }
        else{//平板
            replaceFragment(new MeFragment());
            final Button offline=root.findViewById(R.id.force_offline);

            offline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent("com.example.myschoolapp.FORCE_OFFLINE");
                    maincontext.sendBroadcast(intent);
                }
            });
        }


        return root;
    }

    private void replaceFragment (Fragment fragment){
        System.out.println("设置了布局");
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.right_func, fragment);
        transaction.addToBackStack(null);  // study the difference w/o this line
        transaction.commit();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        System.out.println("隐藏和显示");
        refreshdata();
    }

    public void refreshdata(){
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
}
