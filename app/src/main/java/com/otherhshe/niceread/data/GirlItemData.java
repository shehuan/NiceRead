package com.otherhshe.niceread.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: Othershe
 * Time: 2016/8/17 10:12
 */
public class GirlItemData implements Parcelable {
    private String title;
    private String url;
    private String id;


    public GirlItemData() {
    }

    public GirlItemData(String title, String url, String detailUrl) {
        this.title = title;
        this.url = url;
        this.id = detailUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String detailUrl) {
        this.id = detailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeString(this.id);
    }

    protected GirlItemData(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<GirlItemData> CREATOR = new Parcelable.Creator<GirlItemData>() {
        @Override
        public GirlItemData createFromParcel(Parcel source) {
            return new GirlItemData(source);
        }

        @Override
        public GirlItemData[] newArray(int size) {
            return new GirlItemData[size];
        }
    };
}
