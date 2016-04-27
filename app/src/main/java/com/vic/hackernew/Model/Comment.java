package com.vic.hackernew.Model;

/**
 * Created by vic on 21-Apr-16.
 */
public class Comment implements Comparable<Comment> {
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
}
