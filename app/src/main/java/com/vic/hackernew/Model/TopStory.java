package com.vic.hackernew.Model;

import com.vic.hackernew.Utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by vic on 21-Apr-16.
 */
public class TopStory {
    private int id;
    private String title;
    private String author;
    private int score;
    private int[] kids;
    private int timestamp;

    public TopStory() {
    }

    public TopStory(int id, String title, String author, int point, int timestamp) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.score = point;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
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


    // Decodes business json into business model object
    public static TopStory fromJson(JSONObject jsonObject) {
        TopStory topStory = new TopStory();
        // Deserialize json into object fields
        try {
            topStory.id = jsonObject.getInt(Constant.TAG_ID);
            topStory.title = jsonObject.getString(Constant.TAG_TITLE);
            topStory.author = jsonObject.getString(Constant.TAG_AUTHOR);
            topStory.timestamp = jsonObject.getInt(Constant.TAG_TIMESTAMP);

//            JSONArray kidArray = new JSONArray();
//            kidArray = jsonObject.getJSONArray(Constant.TAG_KIDS);
//
//            for (int i = 0; i <kidArray.length() ; i++) {
//                topStory.kids[i]=kidArray.getJSONObject(i).getInt()
//            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return topStory;
    }

    public static int [] getListTopStoriesId (JSONArray jsonArray){

        int[] listTopStoriesId = new int[]{};

        for (int i = 0; i <jsonArray.length() ; i++) {
            try {
                listTopStoriesId[i]=jsonArray.getJSONObject(i).getInt("");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        return listTopStoriesId;
    }

}
