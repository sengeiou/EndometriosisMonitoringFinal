package com.benlefevre.endometriosismonitoring.models;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

public class FirestorePain {

    private Date date;
    private int intensity;
    private String location;
    private List<String> symptoms;
    private String mood;

    public FirestorePain() {
    }

    public FirestorePain(Date date, int intensity, String location) {
        this.date = date;
        this.intensity = intensity;
        this.location = location;
    }

    public FirestorePain(Date date, int intensity, String location, List<String> symptoms, String mood) {
        this.date = date;
        this.intensity = intensity;
        this.location = location;
        this.symptoms = symptoms;
        this.mood = mood;
    }

//    ------------------------------------Getters---------------------------------------------------

    public Date getDate() {
        return date;
    }

    public int getIntensity() {
        return intensity;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    public String getMood() {
        return mood;
    }

//    ------------------------------------Getters---------------------------------------------------


    public void setDate(Date date) {
        this.date = date;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }


    @NotNull
    @Override
    public String toString() {
        return "FirestorePain{" +
                "date=" + date +
                ", intensity=" + intensity +
                ", location='" + location + '\'' +
                ", symptoms=" + symptoms +
                ", mood='" + mood + '\'' +
                '}';
    }
}
