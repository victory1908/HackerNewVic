package com.vic.hackernew.Model;

import com.vic.hackernew.Utils.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by vic on 21-Apr-16.
 */
public class Comment implements Comparable<Comment> {
    private int id;
    private String text;
    private String author;
    private JSONArray kids;
    private long time;

    public Comment() {
    }

    public Comment(int id, String content, String author, JSONArray kids, long time) {
        this.id = id;
        this.text = content;
        this.author = author;
        this.kids = kids;
        this.time = time;
    }

    public static Comment fromJson(JSONObject jsonObject) {
        Comment comment = new Comment();

        // Deserialize json into object fields
        comment.id = jsonObject.optInt(Constant.TAG_ID);
        comment.text = jsonObject.optString(Constant.TAG_TEXT);
        comment.author = jsonObject.optString(Constant.TAG_AUTHOR);
        comment.kids = jsonObject.optJSONArray(Constant.TAG_KIDS);
        comment.time = jsonObject.optLong(Constant.TAG_TIME);
        // Return new object
        return comment;
    }

    public JSONArray getKids() {
        return kids;
    }

    public void setKids(JSONArray kids) {
        this.kids = kids;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
        return (int) (this.getTime() - another.getTime());
    }
}
