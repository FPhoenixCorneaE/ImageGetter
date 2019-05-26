package com.wkz.imagegetter.gallery;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ImageAttr implements Serializable,Parcelable {

    //序列化ID
    private static final long serialVersionUID = -5809782578272943999L;
    private String url;
    private String thumbnailUrl;

    // 显示的宽高
    private int width;
    private int height;

    // 左上角坐标
    private int left;
    private int top;

    public ImageAttr() {
    }

    protected ImageAttr(Parcel in) {
        url = in.readString();
        thumbnailUrl = in.readString();
        width = in.readInt();
        height = in.readInt();
        left = in.readInt();
        top = in.readInt();
    }

    public static final Creator<ImageAttr> CREATOR = new Creator<ImageAttr>() {
        @Override
        public ImageAttr createFromParcel(Parcel in) {
            return new ImageAttr(in);
        }

        @Override
        public ImageAttr[] newArray(int size) {
            return new ImageAttr[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public ImageAttr setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public ImageAttr setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public ImageAttr setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public ImageAttr setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getLeft() {
        return left;
    }

    public ImageAttr setLeft(int left) {
        this.left = left;
        return this;
    }

    public int getTop() {
        return top;
    }

    public ImageAttr setTop(int top) {
        this.top = top;
        return this;
    }

    @Override
    public String toString() {
        return "ImageAttr{" +
                "url='" + url + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", left=" + left +
                ", top=" + top +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(thumbnailUrl);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeInt(left);
        dest.writeInt(top);
    }
}
