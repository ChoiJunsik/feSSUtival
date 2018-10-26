package com.example.junsikchoi.ssu_festival;

public class NotificationModel {

    public String to;

    public Notification notification = new Notification();

    public static class Notification {
        public String title;
        public String text;
    }
}