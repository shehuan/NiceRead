package com.otherhshe.niceread.model;

/**
 * Author: Othershe
 * Time:  2016/8/11 14:28
 */
public class SplashData {
    public String text;
    public String img;

    public String getName() {
        return text;
    }

    public void setName(String name) {
        this.text = name;
    }

    public String getUrl() {
        return img;
    }

    public void setUrl(String url) {
        this.img = url;
    }
}
