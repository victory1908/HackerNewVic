package com.vic.hackernew.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vic on 21-Apr-16.
 */
public class Comment implements Comparable<Comment>, Parcelable {
    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
    private int id;
    private String text = "";
    private String by;
    private int[] kids;
    private long time;

    public Comment() {
    }

    public Comment(int id, String text, String by, int[] kids, long time) {
        this.id = id;
        this.text = text;
        this.by = by;
        this.kids = kids;
        this.time = time;
    }

    protected Comment(Parcel in) {
        this.id = in.readInt();
        this.text = in.readString();
        this.by = in.readString();
        this.kids = in.createIntArray();
        this.time = in.readLong();
    }

    public int[] getKids() {
        return kids;
    }

    public void setKids(int[] kids) {
        this.kids = kids;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Comment another) {
        return (int) (another.getTime() - this.getTime());
    }

    //Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.text);
        dest.writeString(this.by);
        dest.writeIntArray(this.kids);
        dest.writeLong(this.time);
    }
}
