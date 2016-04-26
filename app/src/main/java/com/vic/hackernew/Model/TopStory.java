package com.vic.hackernew.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by vic on 21-Apr-16.
 */
@Parcel
public class TopStory implements Comparable<TopStory> {
    int id;
    String title;
    String by;
    int score;
    List<Integer> kids;
    long time;
    String url;



    public TopStory() {
    }

    public TopStory(int id, String title, String author, int point, long time,String url) {
        this.id = id;
        this.title = title;
        this.by = author;
        this.score = point;
        this.time = time;
        this.url = url;
    }

    // Decodes business json into business model object
    public static TopStory fromJson(JSONObject jsonObject) {
        TopStory topStory = new TopStory();


        Type listType = new TypeToken<List<TopStory>>(){}.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(listType,new TopStory()).create();


//        // Deserialize json into object fields
//        topStory.id = jsonObject.optInt(Constant.TAG_ID);
//        topStory.title = jsonObject.optString(Constant.TAG_TITLE);
//        topStory.by = jsonObject.optString(Constant.TAG_AUTHOR);
//        topStory.score = jsonObject.optInt(Constant.TAG_SCORE);
//        topStory.time = jsonObject.optLong(Constant.TAG_TIME);
//        topStory.url = jsonObject.optString(Constant.TAG_URL);

        topStory.id = gson.fromJson(jsonObject.toString(),,TopStory.class);

//        topStory.url = gson.fromJson(jsonObject,String.class);
//
//        topStory.kids = gson.fromJson(Constant.TAG_KIDS,jsonObject,new TypeToken<Integer>(){}.getType());

        // Return new object
        return topStory;
    }

    public static int[] getListTopStoriesId(JSONArray jsonArray) {

        int[] listTopStoriesId = new int[]{};

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                listTopStoriesId[i] = jsonArray.getJSONObject(i).getInt("");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        return listTopStoriesId;
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

    public List<Integer> getKids() {
        return kids;
    }

    public void setKids(List<Integer> kids) {
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
}

//class TopStoryTime implements Comparator<TopStory> {
//    @Override
//    public int compare(TopStory lhs, TopStory rhs) {
//        return (int) (rhs.getTime()-lhs.getTime());
//    }
//}