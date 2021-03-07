package com.example.myschoolapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import static com.example.myschoolapp.Constant.current_username;
import static com.example.myschoolapp.Constant.current_userpassword;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(current_username+" This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}