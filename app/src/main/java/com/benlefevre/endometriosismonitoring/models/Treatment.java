package com.benlefevre.endometriosismonitoring.models;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class Treatment {

    private String name;
    private String duration;
    private String dosage;
    private String conditioning;
    private String morningHour;
    private String noonHour;
    private String afternoonHour;
    private String eveningHour;

    public Treatment() {
    }

    public Treatment(String name, String duration, String dosage, String conditioning, String morningHour, String noonHour, String afternoonHour, String eveningHour) {
        this.name = name;
        this.duration = duration;
        this.dosage = dosage;
        this.conditioning = conditioning;
        this.morningHour = morningHour;
        this.noonHour = noonHour;
        this.afternoonHour = afternoonHour;
        this.eveningHour = eveningHour;
    }

//    ---------------------------------------Getters------------------------------------------------
    @NonNull
    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    public String getDosage() {
        return dosage;
    }

    public String getConditioning() {
        return conditioning;
    }

    public String getMorningHour() {
        return morningHour;
    }

    public String getNoonHour() {
        return noonHour;
    }

    public String getAfternoonHour() {
        return afternoonHour;
    }

    public String getEveningHour() {
        return eveningHour;
    }

//    ---------------------------------------Setters------------------------------------------------
    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setConditioning(String conditioning) {
        this.conditioning = conditioning;
    }

    public void setMorningHour(String morningHour) {
        this.morningHour = morningHour;
    }

    public void setNoonHour(String noonHour) {
        this.noonHour = noonHour;
    }

    public void setAfternoonHour(String afternoonHour) {
        this.afternoonHour = afternoonHour;
    }

    public void setEveningHour(String eveningHour) {
        this.eveningHour = eveningHour;
    }

    @NotNull
    @Override
    public String toString() {
        return "Treatment{" +
                "name='" + name + '\'' +
                ", duration='" + duration + '\'' +
                ", dosage='" + dosage + '\'' +
                ", conditioning='" + conditioning + '\'' +
                ", morningHour='" + morningHour + '\'' +
                ", noonHour='" + noonHour + '\'' +
                ", afternoonHour='" + afternoonHour + '\'' +
                ", eveningHour='" + eveningHour + '\'' +
                '}';
    }
}
