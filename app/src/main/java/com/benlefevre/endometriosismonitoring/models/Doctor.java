package com.benlefevre.endometriosismonitoring.models;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Doctor {

    private String mId;
    private String mName;
    private String mCivilite;
    private String mAddress;
    private String mSpec;
    private String mConvention;
    private String mPhone;
    private String mActes;
    private String mTypeActes;
    private List<Double> mCoordonnees;
    private int mNbCommentaries;
    private int rating;

    public Doctor(String id, String name, String civilite, String address, String spec, String convention, String phone,
                  String actes, String typeActes, List<Double> coordonnees) {
        mId = id;
        mName = name;
        mCivilite = civilite;
        mAddress = address;
        mSpec = spec;
        mConvention = convention;
        mPhone = phone;
        mActes = actes;
        mTypeActes = typeActes;
        mCoordonnees = coordonnees;
    }

//    ------------------------------------Getters---------------------------------------------------

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getCivilite() {
        return mCivilite;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getSpec() {
        return mSpec;
    }

    public String getConvention() {
        return mConvention;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getActes() {
        return mActes;
    }

    public String getTypeActes() {
        return mTypeActes;
    }

    public List<Double> getCoordonnees() {
        return mCoordonnees;
    }

    public int getCommentaries() {
        return mNbCommentaries;
    }

    public int getRating() {
        return rating;
    }
    //    ------------------------------------Setters---------------------------------------------------

    public void setId(String id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setCivilite(String civilite) {
        mCivilite = civilite;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public void setSpec(String spec) {
        mSpec = spec;
    }

    public void setConvention(String convention) {
        mConvention = convention;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public void setActes(String actes) {
        mActes = actes;
    }

    public void setTypeActes(String typeActes) {
        mTypeActes = typeActes;
    }

    public void setCoordonnees(List<Double> coordonnees) {
        mCoordonnees = coordonnees;
    }

    public void setCommentaries(int commentaries) {
        mNbCommentaries = commentaries;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @NotNull
    @Override
    public String toString() {
        return "Doctor{" +
                "mId='" + mId + '\'' +
                ", mName='" + mName + '\'' +
                ", mCivilite='" + mCivilite + '\'' +
                ", mAddress='" + mAddress + '\'' +
                ", mSpec='" + mSpec + '\'' +
                ", mConvention='" + mConvention + '\'' +
                ", mPhone='" + mPhone + '\'' +
                ", mActes='" + mActes + '\'' +
                ", mTypeActes='" + mTypeActes + '\'' +
                ", mCoordonnees=" + mCoordonnees +
                ", mNbCommentaries=" + mNbCommentaries +
                ", rating=" + rating +
                '}';
    }
}
