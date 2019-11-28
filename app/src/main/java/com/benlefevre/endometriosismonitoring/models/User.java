package com.benlefevre.endometriosismonitoring.models;

import org.jetbrains.annotations.NotNull;

public class User {

    private String mId;
    private String mName;
    private String mMail;
    private String mPhotoUrl;

    public User() {
    }

    public User(String id, String name, String mail, String photoUrl) {
        mId = id;
        mName = name;
        mMail = mail;
        mPhotoUrl = photoUrl;
    }

//    -------------------------------------Getters--------------------------------------------------

    public String getId() {

        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getMail() {
        return mMail;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

//    -------------------------------------Setters--------------------------------------------------

    public void setId(String id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setMail(String mail) {
        mMail = mail;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }


    @NotNull
    @Override
    public String toString() {
        return "User{" +
                "mId='" + mId + '\'' +
                ", mName='" + mName + '\'' +
                ", mMail='" + mMail + '\'' +
                ", mPhotoUrl='" + mPhotoUrl + '\'' +
                '}';
    }
}
