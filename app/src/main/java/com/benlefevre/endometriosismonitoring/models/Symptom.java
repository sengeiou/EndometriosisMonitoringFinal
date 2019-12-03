package com.benlefevre.endometriosismonitoring.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Pain.class, parentColumns = "id", childColumns = "painId", onDelete = CASCADE))
public class Symptom {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long painId;
    private String name;
    private Date date;

    public Symptom() {
    }

    @Ignore
    public Symptom(long painId, String name, Date date) {
        this.painId = painId;
        this.name = name;
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

    public void setDate(Date date) {
        this.date = date;
    }

    @NonNull
    @Override
    public String toString() {
        return "Symptoms{" +
                "painId=" + painId +
                ", name='" + name + '\'' +
                ",date='" + date + '\'' +
                '}';
    }
}
