package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "patient_table")
public class Patient {
    @PrimaryKey(autoGenerate = true)
    private int patientID;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String department;

    @NonNull
    private int nurseID;

    @NonNull
    private String room;

    public Patient(@NonNull String firstName, @NonNull String lastName,
                   @NonNull String department, @NonNull int nurseID, @NonNull String room) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.nurseID = nurseID;
        this.room = room;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    public String getDepartment() {
        return department;
    }

    public void setDepartment(@NonNull String department) {
        this.department = department;
    }

    public int getNurseID() {
        return nurseID;
    }

    public void setNurseID(int nurseID) {
        this.nurseID = nurseID;
    }

    @NonNull
    public String getRoom() {
        return room;
    }

    public void setRoom(@NonNull String room) {
        this.room = room;
    }
}
