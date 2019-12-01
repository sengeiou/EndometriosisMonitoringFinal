package com.benlefevre.endometriosismonitoring.models;

import org.jetbrains.annotations.NotNull;

public class DoctorCommentaryCounter {

    private int nbCommentaries;
    private int rating;
    private String doctorId;

    public DoctorCommentaryCounter() {
    }

    public DoctorCommentaryCounter(int nbCommentaries, int rating, String doctorId) {
        this.nbCommentaries = nbCommentaries;
        this.rating = rating;
        this.doctorId = doctorId;
    }

//    -------------------------------------Getters--------------------------------------------------

    public int getNbCommentaries() {
        return nbCommentaries;
    }

    public int getRating() {
        return rating;
    }

    public String getDoctorId() {
        return doctorId;
    }

//    -------------------------------------Setters--------------------------------------------------


    public void setNbCommentaries(int nbCommentaries) {
        this.nbCommentaries = nbCommentaries;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }


    @NotNull
    @Override
    public String toString() {
        return "DoctorCommentaryCounter{" +
                "nbCommentaries=" + nbCommentaries +
                ", rating=" + rating +
                ", doctorId='" + doctorId + '\'' +
                '}';
    }
}
