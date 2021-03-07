package com.example.myschoolapp.ui.intranet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.myschoolapp.ContentActivity;
import com.example.myschoolapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import static com.example.myschoolapp.Constant.maincontext;
import static com.example.myschoolapp.Constant.sizemode;

public class IntranetFragment extends Fragment {

    private IntranetViewModel intranetViewModel;
    public SectionsPagerAdapter sectionsPagerAdapter;
    int current_page_index=0;
    TextView bigtitle;
    ListView[] listViews=new ListView[4];

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        intranetViewModel =
                ViewModelProviders.of(this).get(IntranetViewModel.class);

        View root = inflater.inflate(R.layout.fragment_intranet, container, false);
        bigtitle=root.findViewById(R.id.big_title);
        if(bigtitle.getText().toString().equals("内部网")){
            sectionsPagerAdapter = new SectionsPagerAdapter(maincontext, getChildFragmentManager());
            final ViewPager viewPager = root.findViewById(R.id.view_pager);
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = root.findViewById(R.id.tabs);
            tabs.addTab(tabs.newTab().setText("教师事务"));
            tabs.addTab(tabs.newTab().setText("学生事务"));
            tabs.addTab(tabs.newTab().setText("荔园生活"));
            tabs.addTab(tabs.newTab().setText("网上服务"));

            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
            tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                // 被选中的时候
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    current_page_index= tab.getPosition();
                    viewPager.setCurrentItem(current_page_index);
                    System.out.println("nowpage:"+current_page_index);
                }
                // 没有被选中的时候
                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }
                // 重现被选中的时候
                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
        else {
            listViews[0]=root.findViewById(R.id.listview1);
            listViews[1]=root.findViewById(R.id.listview2);
            listViews[2]=root.findViewById(R.id.listview3);
            listViews[3]=root.findViewById(R.id.listview4);
            for(int i=0;i<4;i++)
            {
                IntranetFunctionUtils intranetFunctionUtils=new IntranetFunctionUtils();

                final List<IntranetFunction> intranetFunctionList=intranetFunctionUtils.getlist(i+1);
                System.out.println(i+1+":新闻数："+intranetFunctionList.size());
                IntranetFunctionAdapter intranetFunctionAdapter=new IntranetFunctionAdapter(intranetFunctionList);
                listViews[i].setAdapter(intranetFunctionAdapter);
                listViews[i].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    Intent intent = new Intent(maincontext, ContentActivity.class);
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        intent.putExtra("position",String.valueOf(position));
                        intent.putExtra("title",intranetFunctionList.get(position).getTitle());
                        intent.putExtra("url",intranetFunctionList.get(position).getUrl());
                        for(int i=0;i<intranetFunctionList.size();i++){
                            System.out.println(intranetFunctionList.get(i).getUrl());
                        }
                        startActivity(intent);
                    }
                });
            }

        }
        return root;
    }
}
