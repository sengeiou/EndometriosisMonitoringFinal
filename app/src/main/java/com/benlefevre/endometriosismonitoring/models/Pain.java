package com.benlefevre.endometriosismonitoring.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Pain {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private Date date;
    private int intensity;
    private String location;

    public Pain() {
    }

    @Ignore
    public Pain(Date date, int intensity, String location) {
        this.date = date;
        this.intensity = intensity;
        this.location = location;
    }


    //    ---------------------------------------Getters------------------------------------------------
    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int getIntensity() {
        return intensity;
    }

    public String getLocation() {
        return location;
    }

    //    ---------------------------------------Setters------------------------------------------------
    public void setId(long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @NonNull
    @Override
    public String toString() {
        return "Pain{" +
                "id=" + id +
                ", date=" + date +
                ", intensity=" + intensity +
                ", location='" + location + '\'' +
                '}';
    }
}
