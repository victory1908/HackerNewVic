package com.vic.hackernew.Model;

import com.vic.hackernew.Utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vic on 21-Apr-16.
 */
public class TopStory implements Comparable<TopStory> {
    private int id;
    private String title;
    private String author;
    private int score;
    private JSONArray kids;
    private long time;
    private String url;

    public TopStory() {
    }

    public TopStory(int id, String title, String author, int point, long time,String url) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.score = point;
        this.time = time;
        this.url = url;
    }

    // Decodes business json into business model object
    public static TopStory fromJson(JSONObject jsonObject) {
        TopStory topStory = new TopStory();

        // Deserialize json into object fields
        topStory.id = jsonObject.optInt(Constant.TAG_ID);
        topStory.title = jsonObject.optString(Constant.TAG_TITLE);
        topStory.author = jsonObject.optString(Constant.TAG_AUTHOR);
        topStory.score = jsonObject.optInt(Constant.TAG_SCORE);
        topStory.time = jsonObject.optLong(Constant.TAG_TIME);
        topStory.url = jsonObject.optString(Constant.TAG_URL);
        topStory.kids = jsonObject.optJSONArray(Constant.TAG_KIDS);
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

    public JSONArray getKids() {
        return kids;
    }

    public void setKids(JSONArray kids) {
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