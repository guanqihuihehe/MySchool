package com.example.myschoolapp.ui.intranet;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;


import java.util.List;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();

    private LiveData<List<IntranetFunction>> mText = Transformations.map(mIndex, new Function<Integer, List<IntranetFunction>>() {
        @Override
        public List<IntranetFunction> apply(Integer input) {

            System.out.println("请求了"+input);
            IntranetFunctionUtils intranetFunctionUtils=new IntranetFunctionUtils();
//            intranetFunctionUtils.setIndex(input);

            List<IntranetFunction> intranetFunctionList=intranetFunctionUtils.getlist(input);
            System.out.println(input+":新闻数："+intranetFunctionList.size());
//            NewsAdapter newsAdapter=new NewsAdapter(newsList);
            return intranetFunctionList;
        }
    });
//
    public void setIndex(int index) {
        mIndex.setValue(index);
    }
//
    public LiveData<List<IntranetFunction>> getText() {
        return mText;
    }
}