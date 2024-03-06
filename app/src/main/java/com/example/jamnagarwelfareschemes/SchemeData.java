package com.example.jamnagarwelfareschemes;

import java.util.List;

public class SchemeData {
    List<String> Document;
    String name,detail,catagory,eligibility,deadline;

    public SchemeData(){}

    public SchemeData(List<String> Document, String name, String detail, String catagory, String eligibility, String deadline) {
        this.Document = Document;
        this.name = name;
        this.detail = detail;
        this.catagory = catagory;
        this.eligibility = eligibility;
        this.deadline = deadline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCatagories() {
        return catagory;
    }

    public void setCatagories(String catagory) {
        this.catagory = catagory;
    }

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public List<String> getDocument() {
        return Document;
    }

    public void setCheckbox(List<String> Document) {
        this.Document = Document;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
