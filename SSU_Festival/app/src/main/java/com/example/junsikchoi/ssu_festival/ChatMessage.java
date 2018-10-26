package com.example.junsikchoi.ssu_festival;

public class ChatMessage {

    private String id;          // DB에 저장할 ID
    private String text;        // 메시지
    private String name;        // 이름
    private double rating;      //별점

    public ChatMessage() {
    }

    public ChatMessage(String text, String name,double rating) {
        this.text = text;
        this.name = name;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating(){return rating;}
}