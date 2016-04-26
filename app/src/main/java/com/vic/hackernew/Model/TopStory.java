package com.vic.hackernew.Model;

import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

/**
 * Created by vic on 21-Apr-16.
 */

public class TopStory implements Parcelable, Comparable<TopStory> {


    public static final Creator<TopStory> CREATOR = new Creator<TopStory>() {
        @Override
        public TopStory createFromParcel(android.os.Parcel source) {
            return new TopStory(source);
        }

        @Override
        public TopStory[] newArray(int size) {
            return new TopStory[size];
        }
    };
    String by;
    int id;
    int[] kids;
    int score;
    long time;
    String title;
    String url;

    public TopStory() {
    }


    public TopStory(String by, int id, int[] kids, int score, long time, String title, String url) {
        this.by = by;
        this.id = id;
        this.kids = kids;
        this.score = score;
        this.time = time;
        this.title = title;
        this.url = url;
    }


    protected TopStory(android.os.Parcel in) {
        this.by = in.readString();
        this.id = in.readInt();
        this.kids = in.createIntArray();
        this.score = in.readInt();
        this.time = in.readLong();
        this.title = in.readString();
        this.url = in.readString();
    }

    // Decodes business json into business model object
    public static TopStory fromJson(JSONObject jsonObject) {

        Gson gson = new GsonBuilder().create();
        TopStory topStory = gson.fromJson(jsonObject.toString(), TopStory.class);

//        Type listType = new TypeToken<List<TopStory>>(){}.getType();
//        Gson gson = new GsonBuilder().registerTypeAdapter(listType,new TopStory()).create();


//        // Deserialize json into object fields
//        topStory.id = jsonObject.optInt(Constant.TAG_ID);
//        topStory.title = jsonObject.optString(Constant.TAG_TITLE);
//        topStory.by = jsonObject.optString(Constant.TAG_AUTHOR);
//        topStory.score = jsonObject.optInt(Constant.TAG_SCORE);
//        topStory.time = jsonObject.optLong(Constant.TAG_TIME);
//        topStory.url = jsonObject.optString(Constant.TAG_URL);

//        topStory.url = gson.fromJson(jsonObject,String.class);
//
//        topStory.kids = gson.fromJson(Constant.TAG_KIDS,jsonObject,new TypeToken<Integer>(){}.getType());

        // Return new object
        return topStory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public int[] getKids() {
        return kids;
    }

    public void setKids(int[] kids) {
        this.kids = kids;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int compareTo(TopStory another) {
        return (int) ((another.getTime()) - this.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(this.by);
        dest.writeInt(this.id);
        dest.writeIntArray(this.kids);
        dest.writeInt(this.score);
        dest.writeLong(this.time);
        dest.writeString(this.title);
        dest.writeString(this.url);
    }
}