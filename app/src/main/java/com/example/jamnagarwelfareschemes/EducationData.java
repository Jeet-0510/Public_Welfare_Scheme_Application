package com.example.jamnagarwelfareschemes;

public class EducationData {
    String fees, SSCBoard, SSCPY, SSCSchool, SSCPercentage, HSCBoard, HSCPY, HSCSchool, HSCPercentage, BachUniversity, BachDegree, backGY, BachCgpa;

    public EducationData() {
    }

    public EducationData(String fees, String SSCBoard, String SSCPY, String SSCSchool, String SSCPercentage, String HSCBoard, String HSCPY, String HSCSchool, String HSCPercentage, String bachUniversity, String bachDegree, String backGY, String bachCgpa) {
        this.fees = fees;
        this.SSCBoard = SSCBoard;
        this.SSCPY = SSCPY;
        this.SSCSchool = SSCSchool;
        this.SSCPercentage = SSCPercentage;
        this.HSCBoard = HSCBoard;
        this.HSCPY = HSCPY;
        this.HSCSchool = HSCSchool;
        this.HSCPercentage = HSCPercentage;
        BachUniversity = bachUniversity;
        BachDegree = bachDegree;
        this.backGY = backGY;
        BachCgpa = bachCgpa;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getSSCBoard() {
        return SSCBoard;
    }

    public void setSSCBoard(String SSCBoard) {
        this.SSCBoard = SSCBoard;
    }

    public String getSSCPY() {
        return SSCPY;
    }

    public void setSSCPY(String SSCPY) {
        this.SSCPY = SSCPY;
    }

    public String getSSCSchool() {
        return SSCSchool;
    }

    public void setSSCSchool(String SSCSchool) {
        this.SSCSchool = SSCSchool;
    }

    public String getSSCPercentage() {
        return SSCPercentage;
    }

    public void setSSCPercentage(String SSCPercentage) {
        this.SSCPercentage = SSCPercentage;
    }

    public String getHSCBoard() {
        return HSCBoard;
    }

    public void setHSCBoard(String HSCBoard) {
        this.HSCBoard = HSCBoard;
    }

    public String getHSCPY() {
        return HSCPY;
    }

    public void setHSCPY(String HSCPY) {
        this.HSCPY = HSCPY;
    }

    public String getHSCSchool() {
        return HSCSchool;
    }

    public void setHSCSchool(String HSCSchool) {
        this.HSCSchool = HSCSchool;
    }

    public String getHSCPercentage() {
        return HSCPercentage;
    }

    public void setHSCPercentage(String HSCPercentage) {
        this.HSCPercentage = HSCPercentage;
    }

    public String getBachUniversity() {
        return BachUniversity;
    }

    public void setBachUniversity(String bachUniversity) {
        BachUniversity = bachUniversity;
    }

    public String getBachDegree() {
        return BachDegree;
    }

    public void setBachDegree(String bachDegree) {
        BachDegree = bachDegree;
    }

    public String getBackGY() {
        return backGY;
    }

    public void setBackGY(String backGY) {
        this.backGY = backGY;
    }

    public String getBachCgpa() {
        return BachCgpa;
    }

    public void setBachCgpa(String bachCgpa) {
        BachCgpa = bachCgpa;
    }
}
