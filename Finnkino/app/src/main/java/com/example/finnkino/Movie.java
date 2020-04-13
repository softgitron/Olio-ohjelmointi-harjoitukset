package com.example.finnkino;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Movie {
    private Integer id;
    private String title;
    private String theatre;
    private String auditorium;
    private String picture;
    private Date startTime;
    private Date endTime;

    public Movie(Integer id, String title, String theatre, String auditorium, Date startTime, Date endTime) {
        this.id = id;
        this.title = title;
        this.theatre = theatre;
        this.auditorium = auditorium;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public void setPicture(String picture) { this.picture = picture; }

    public Integer getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getTheatre() {
        return theatre;
    }
    public String getAuditorium() {
        return auditorium;
    }
    public String getPicture() {
        return picture;
    }
    public String getStartTimeString() {return new SimpleDateFormat("HH:mm").format(startTime);}
    public String getEndTimeString() {return new SimpleDateFormat("HH:mm").format(endTime);};
    public Date getStartTime() { return startTime; }
    public Date getEndTime() { return endTime; }

}
