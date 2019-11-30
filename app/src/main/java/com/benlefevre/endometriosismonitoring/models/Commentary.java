package com.benlefevre.endometriosismonitoring.models;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class Commentary {

    private String mDoctorId;
    private String mAuthorName;
    private String mAuthorPhoto;
    private int mRating;
    private String mUserInput;
    private Date mDate;

    public Commentary() {
    }

    public Commentary(String doctorId, String authorName, String authorPhoto,
                      int rating, String userInput, Date date) {
        mDoctorId = doctorId;
        mAuthorName = authorName;
        mAuthorPhoto = authorPhoto;
        mRating = rating;
        mUserInput = userInput;
        mDate = date;
    }

//    ------------------------------------Getters---------------------------------------------------

    public String getDoctorId() {
        return mDoctorId;
    }

    public String getAuthorName() {
        return mAuthorName;
    }

    public String getAuthorPhoto() {
        return mAuthorPhoto;
    }

    public int getRating() {
        return mRating;
    }

    public String getUserInput() {
        return mUserInput;
    }

    public Date getDate() {
        return mDate;
    }
//    ------------------------------------Setters---------------------------------------------------

    public void setDoctorId(String doctorId) {
        mDoctorId = doctorId;
    }

    public void setAuthorName(String authorName) {
        mAuthorName = authorName;
    }

    public void setAuthorPhoto(String authorPhoto) {
        mAuthorPhoto = authorPhoto;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

    public void setUserInput(String userInput) {
        mUserInput = userInput;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    @NotNull
    @Override
    public String toString() {
        return "Commentary{" +
                "mDoctorId='" + mDoctorId + '\'' +
                ", mAuthorName='" + mAuthorName + '\'' +
                ", mAuthorPhoto='" + mAuthorPhoto + '\'' +
                ", mRating=" + mRating +
                ", mUserInput='" + mUserInput + '\'' +
                ", mDate=" + mDate +
                '}';
    }
}
