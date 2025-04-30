package com.UCI.redditsystem;

public class Msg {
    private String id;
    private String parentid;
    private String title;
    private String content;
    private String score;

    public Msg() {
    }

    public Msg(String id, String parentid, String title, String content, String score) {
        this.id = id;
        this.parentid = parentid;
        this.title = title;
        this.content = content;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
