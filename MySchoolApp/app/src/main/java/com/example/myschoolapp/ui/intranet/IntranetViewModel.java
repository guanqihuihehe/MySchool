package com.example.myschoolapp.ui.intranet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import static com.example.myschoolapp.Constant.current_username;
import static com.example.myschoolapp.Constant.current_userpassword;

public class IntranetViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public IntranetViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(current_username+"This is Intranet fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}