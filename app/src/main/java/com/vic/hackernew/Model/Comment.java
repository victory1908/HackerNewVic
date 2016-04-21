package com.vic.hackernew.Model;

import java.sql.Timestamp;

/**
 * Created by vic on 21-Apr-16.
 */
public class Comment {
    private String id;
    private String content;
    private String author;
    private Timestamp timestamp;

    public Comment() {
    }

    public Comment(String content, String author, Timestamp timestamp) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




}
