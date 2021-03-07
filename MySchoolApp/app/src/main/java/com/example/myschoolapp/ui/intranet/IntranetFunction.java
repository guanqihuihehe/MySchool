package com.example.myschoolapp.ui.intranet;

import android.graphics.drawable.Icon;

public class IntranetFunction {
    public String title;
    public int icon;
    public String url;


    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

}
