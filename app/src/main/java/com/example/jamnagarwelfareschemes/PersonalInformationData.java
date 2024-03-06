package com.example.jamnagarwelfareschemes;

public class PersonalInformationData {
    String name, caste, gender, disabled, dob, address, occupation, income, pincode, aadhar, pan;

    public PersonalInformationData() {
    }

    public PersonalInformationData(String name, String caste, String gender, String disabled, String dob, String address, String occupation, String income, String pincode, String aadhar, String pan) {
        this.name = name;
        this.caste = caste;
        this.gender = gender;
        this.disabled = disabled;
        this.dob = dob;
        this.address = address;
        this.occupation = occupation;
        this.income = income;
        this.pincode = pincode;
        this.aadhar = aadhar;
        this.pan = pan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }
}
