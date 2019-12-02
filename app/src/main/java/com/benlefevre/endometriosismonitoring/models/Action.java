package com.benlefevre.endometriosismonitoring.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Pain.class, parentColumns = "id", childColumns = "painId"))
public class Action {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long painId;
    private String name;
    private int duration;
    private int intensity;
    private int painValue;
    private Date date;

    public Action() {
    }

    @Ignore
    public Action(long painId, String name, int duration, int intensity, int painValue, Date date) {
        this.painId = painId;
        this.name = name;
        this.duration = duration;
        this.intensity = intensity;
        this.painValue = painValue;
        this.date = date;
    }

//    ---------------------------------------Getters------------------------------------------------

    public long getId() {
        return id;
    }

    public long getPainId() {
        return painId;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getIntensity() {
        return intensity;
    }

    public int getPainValue() {
        return painValue;
    }

    public Date getDate() {
        return date;
    }

//    ---------------------------------------Setters------------------------------------------------
    public void setId(long id) {
        this.id = id;
    }

    public void setPainId(long painId) {
        this.painId = painId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public void setPainValue(int painValue) {
        this.painValue = painValue;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @NotNull
    @Override
    public String toString() {
        return "Action{" +
                "id=" + id +
                ", painId=" + painId +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", intensity=" + intensity +
                ", painValue=" + painValue +
                ", date=" + date +
                '}';
    }
}
