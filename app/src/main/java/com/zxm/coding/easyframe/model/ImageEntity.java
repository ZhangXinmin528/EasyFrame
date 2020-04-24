package com.zxm.coding.easyframe.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ZhangXinmin on 2020/4/22.
 * Copyright (c) 2020 . All rights reserved.
 */
public class ImageEntity implements Parcelable {
    private String title;
    private String width;
    private String height;
    private String thumb;
    private int thumbWidth;
    private int thumbHeight;

    public ImageEntity() {
    }


    protected ImageEntity(Parcel in) {
        title = in.readString();
        width = in.readString();
        height = in.readString();
        thumb = in.readString();
        thumbWidth = in.readInt();
        thumbHeight = in.readInt();
    }

    public static final Creator<ImageEntity> CREATOR = new Creator<ImageEntity>() {
        @Override
        public ImageEntity createFromParcel(Parcel in) {
            return new ImageEntity(in);
        }

        @Override
        public ImageEntity[] newArray(int size) {
            return new ImageEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(width);
        dest.writeString(height);
        dest.writeString(thumb);
        dest.writeInt(thumbWidth);
        dest.writeInt(thumbHeight);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getThumbWidth() {
        return thumbWidth;
    }

    public void setThumbWidth(int thumbWidth) {
        this.thumbWidth = thumbWidth;
    }

    public int getThumbHeight() {
        return thumbHeight;
    }

    public void setThumbHeight(int thumbHeight) {
        this.thumbHeight = thumbHeight;
    }

    @Override
    public String toString() {
        return "ImageEntity{" +
                "title='" + title + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", thumb='" + thumb + '\'' +
                ", thumbWidth=" + thumbWidth +
                ", thumbHeight=" + thumbHeight +
                '}';
    }
}
