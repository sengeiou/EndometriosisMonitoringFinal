package com.benlefevre.endometriosismonitoring.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Pain.class, parentColumns = "id", childColumns = "painId"))
public class Action {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long painId;
    private String name;
    private int duration;
    private int intensity;
    private int painValue;

    public Action() {
    }

    public Action(long painId, String name, int duration, int intensity) {
        this.painId = painId;
        this.name = name;
        this.duration = duration;
        this.intensity = intensity;
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

    @NonNull
    @Override
    public String toString() {
        return "Action{" +
                "painId=" + painId +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", intensity=" + intensity +
                '}';
    }
}
