package com.otherhshe.niceread.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:34
 */
public class GankItemData implements Parcelable {

    /**
     * _id : 57aca861421aa949ef961f48
     * createdAt : 2016-08-12T00:31:29.798Z
     * desc : 一个简单，强大的广告活动弹窗控件
     * publishedAt : 2016-08-12T11:39:10.578Z
     * source : chrome
     * type : Android
     * url : https://github.com/yipianfengye/android-adDialog
     * used : true
     * who : Jason
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String[] images;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeString(this.createdAt);
        dest.writeString(this.desc);
        dest.writeStringArray(this.images);
        dest.writeString(this.publishedAt);
        dest.writeString(this.source);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeByte(this.used ? (byte) 1 : (byte) 0);
        dest.writeString(this.who);
    }

    public GankItemData() {
    }

    protected GankItemData(Parcel in) {
        this._id = in.readString();
        this.createdAt = in.readString();
        this.desc = in.readString();
        this.images = in.createStringArray();
        this.publishedAt = in.readString();
        this.source = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.used = in.readByte() != 0;
        this.who = in.readString();
    }

    public static final Parcelable.Creator<GankItemData> CREATOR = new Parcelable.Creator<GankItemData>() {
        @Override
        public GankItemData createFromParcel(Parcel source) {
            return new GankItemData(source);
        }

        @Override
        public GankItemData[] newArray(int size) {
            return new GankItemData[size];
        }
    };
}
