package com.benlefevre.endometriosismonitoring.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Pain.class, parentColumns = "id", childColumns = "painId"))
public class Mood {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long painId;
    private String value;

    public Mood() {
    }

    @Ignore
    public Mood(long painId, String value) {
        this.painId = painId;
        this.value = value;
    }

//    ---------------------------------------Getters------------------------------------------------

    public long getId() {
        return id;
    }

    public long getPainId() {
        return painId;
    }

    public String getValue() {
        return value;
    }

//    ---------------------------------------Setters------------------------------------------------

    public void setId(long id) {
        this.id = id;
    }

    public void setPainId(long painId) {
        this.painId = painId;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @NonNull
    @Override
    public String toString() {
        return "Mood{" +
                "painId=" + painId +
                ", value='" + value + '\'' +
                '}';
    }
}
