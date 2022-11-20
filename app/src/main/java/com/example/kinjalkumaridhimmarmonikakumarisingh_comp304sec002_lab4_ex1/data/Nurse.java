package com.example.kinjalkumaridhimmarmonikakumarisingh_comp304sec002_lab4_ex1.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "nurse_table")
public class Nurse {

    @PrimaryKey
    @NonNull
    private int nurseId;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String department;

    @NonNull
    private String password;

    public Nurse(@NonNull int nurseId, @NonNull String firstName, @NonNull String lastName,
                 @NonNull String department, @NonNull String password) {
        this.nurseId = nurseId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.password = password;
    }

    public int getNurseId() {
        return nurseId;
    }

    public void setNurseId(int nurseId) {
        this.nurseId = nurseId;
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

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

}
