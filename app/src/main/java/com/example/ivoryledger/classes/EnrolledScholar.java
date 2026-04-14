package com.example.ivoryledger.classes;

public class EnrolledScholar {
    private int scholarId;
    private String familyName;
    private String givenName;

    public EnrolledScholar(String familyName, String givenName) {
        this.familyName = familyName;
        this.givenName = givenName;
    }

    public EnrolledScholar() {}

    public int getScholarId() {
        return scholarId;
    }

    public void setScholarId(int scholarId) {
        this.scholarId = scholarId;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }
}