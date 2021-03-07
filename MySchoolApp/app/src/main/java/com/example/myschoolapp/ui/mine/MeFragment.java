package com.example.myschoolapp.ui.mine;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import com.example.myschoolapp.R;

public class MeFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private View view;

    public int func_index;

//    public static MeFragment newInstance(int index) {
//        MeFragment fragment = new MeFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt(ARG_SECTION_NUMBER, index);
//        fragment.setArguments(bundle);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        int index = 1;
//        if (getArguments() != null) {
//            index = getArguments().getInt(ARG_SECTION_NUMBER);
//        }
//        func_index=index;
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, container, false);

        return view;
    }

    public void refresh(String newsTitle, String newsContent) {

    }

}
