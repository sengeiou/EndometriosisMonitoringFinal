package com.benlefevre.endometriosismonitoring.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Pain.class, parentColumns = "id", childColumns = "painId"))
public class Symptom {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long painId;
    private String name;

    public Symptom() {
    }

    public Symptom(long painId, String name) {
        this.painId = painId;
        this.name = name;
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


    @NonNull
    @Override
    public String toString() {
        return "Symptoms{" +
                "painId=" + painId +
                ", name='" + name + '\'' +
                '}';
    }
}
