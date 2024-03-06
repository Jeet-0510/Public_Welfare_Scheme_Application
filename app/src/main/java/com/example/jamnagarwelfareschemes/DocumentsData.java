package com.example.jamnagarwelfareschemes;

public class DocumentsData {

    String aadhar, photo, pan, caste, income, ssc, hsc, graduation, pwd, bank, feesReceipt;

    public DocumentsData() {
    }

    public DocumentsData(String aadhar, String photo, String pan, String caste, String income, String ssc, String hsc, String graduation, String pwd, String bank, String feesReceipt) {
        this.aadhar = aadhar;
        this.photo = photo;
        this.pan = pan;
        this.caste = caste;
        this.income = income;
        this.ssc = ssc;
        this.hsc = hsc;
        this.graduation = graduation;
        this.pwd = pwd;
        this.bank = bank;
        this.feesReceipt = feesReceipt;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getSsc() {
        return ssc;
    }

    public void setSsc(String ssc) {
        this.ssc = ssc;
    }

    public String getHsc() {
        return hsc;
    }

    public void setHsc(String hsc) {
        this.hsc = hsc;
    }

    public String getGraduation() {
        return graduation;
    }

    public void setGraduation(String graduation) {
        this.graduation = graduation;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getFeesReceipt() {
        return feesReceipt;
    }

    public void setFeesReceipt(String feesReceipt) {
        this.feesReceipt = feesReceipt;
    }
}
