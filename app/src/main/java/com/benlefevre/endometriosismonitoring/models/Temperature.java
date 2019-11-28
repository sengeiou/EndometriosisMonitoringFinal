package com.benlefevre.endometriosismonitoring.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Entity
public class Temperature {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private double mValue;
    private Date mDate;

    public Temperature(double value, Date date) {
        mValue = value;
        mDate = date;
    }

//    --------------------------------------Getters-------------------------------------------------

    public long getId() {
        return id;
    }

    public double getValue() {
        return mValue;
    }

    public Date getDate() {
        return mDate;
    }

//    --------------------------------------Setters-------------------------------------------------

    public void setId(long id) {
        this.id = id;
    }

    public void setValue(double value) {
        mValue = value;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    @NotNull
    @Override
    public String toString() {
        return "Temperature{" +
                "mValue=" + mValue +
                ", mDate=" + mDate +
                '}';
    }
}
