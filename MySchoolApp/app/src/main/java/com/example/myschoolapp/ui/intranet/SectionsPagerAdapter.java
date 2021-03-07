package com.example.myschoolapp.ui.intranet;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

//    @StringRes
//    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,R.string.tab_text_3};
    private final Context mContext;
    FragmentManager fm;
//    public boolean[] fragmentsUpdateFlag = { false, false, false, false ,false};

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
//        this.fm=fm;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        PlaceholderFragment tempfragment=PlaceholderFragment.newInstance(position + 1);
        return tempfragment;
    }
    @Override
    public int getCount() {
        // Show 5 total pages.
        return 4;
    }

//    @Override
//    public void addObserver(PlaceholderFragment obj) {
//        fragments.add(obj);
//    }
//
//    @Override
//    public void deleteObserver(PlaceholderFragment obj) {
//        fragments.remove(obj);
//    }
//
//    @Override
//    public void notifyObserver() {
////        for (int i =0;i<fragments.size();i++){
////            fragments.get(i).update();
////        }
//    }

//    @Override
//    public int getItemPosition(@NonNull Object object) {
//        return POSITION_NONE;
//    }

//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        Fragment fragment = (Fragment)  super.instantiateItem(container, position);
//        String fragmentTag=fragment.getTag();
//        System.out.println("启动更新：position"+position);
//        if (fragmentsUpdateFlag[position]) {
//            //如果这个fragment需要更新
//            System.out.println("开始更新,position:"+position);
//            FragmentTransaction ft = fm.beginTransaction();
//            //移除旧的fragment
//            ft.remove(fragment);
//            //换成新的fragment
//            fragment = PlaceholderFragment.newInstance(position+1);
//            //添加新fragment时必须用前面获得的tag，这点很重要
//            ft.add(container.getId(), fragment, fragmentTag);
//            ft.attach(fragment);
//            ft.commit();
//
//            //复位更新标志
//            fragmentsUpdateFlag[position] = false;
//        }
//        return fragment;
//    }
}