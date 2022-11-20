package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "test_table")
public class Test {

    @PrimaryKey(autoGenerate = true)
    int testID;

    @NonNull
    int patientID;

    @NonNull
    int nurseID;

    @NonNull
    String bpl;

    @NonNull
    String bph;

    @NonNull
    String temperature;

    @NonNull
    String covidTest;

    @NonNull
    String hivTest;

    //Add More Tests
    public Test(@NonNull int patientID,
                @NonNull int nurseID,
                @NonNull String bpl,@NonNull String bph,
                @NonNull String temperature,
                @NonNull String covidTest,
                @NonNull  String hivTest) {
        this.patientID = patientID;
        this.nurseID = nurseID;
        this.bpl = bpl;
        this.bph = bph;
        this.temperature = temperature;
        this.covidTest = covidTest;
        this.hivTest = hivTest;
    }

    @NonNull
    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    @NonNull
    public int getNurseID() {
        return nurseID;
    }

    public void setNurseID(int nurseID) {
        this.nurseID = nurseID;
    }

    @NonNull
    public String getBpl() {
        return bpl;
    }

    public void setBpl(String bpl) {
        this.bpl = bpl;
    }

    @NonNull
    public String getBph() {
        return bph;
    }

    public void setBph(String bph) {
        this.bph = bph;
    }

    @NonNull
    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @NonNull
    public int getTestID() {
        return testID;
    }

    @NonNull
    public String getCovidTest() {
        return covidTest;
    }

    public void setCovidTest(@NonNull String covidTest) {
        this.covidTest = covidTest;
    }

    @NonNull
    public String getHivTest() {
        return hivTest;
    }

    public void setHivTest(@NonNull String hivTest) {
        this.hivTest = hivTest;
    }
}
