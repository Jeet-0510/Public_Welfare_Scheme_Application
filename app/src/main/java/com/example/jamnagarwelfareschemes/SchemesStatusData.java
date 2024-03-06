package com.example.jamnagarwelfareschemes;

public class SchemesStatusData {

    String ApplicationId, SchemeName, SchemeCatagory, Status, Query, Transaction, DateOfApplication;

    public SchemesStatusData() {
    }

    public SchemesStatusData(String applicationId, String schemeName, String schemeCatagory, String status, String query, String transaction, String DateOfApplication) {
        ApplicationId = applicationId;
        SchemeName = schemeName;
        SchemeCatagory = schemeCatagory;
        Status = status;
        Query = query;
        Transaction = transaction;
        this.DateOfApplication = DateOfApplication;
    }

    public String getDateOfApplication() {
        return DateOfApplication;
    }

    public void setDateOfApplication(String dateOfApplication) {
        DateOfApplication = dateOfApplication;
    }

    public String getTransaction() {
        return Transaction;
    }

    public void setTransaction(String transaction) {
        Transaction = transaction;
    }

    public String getApplicationId() {
        return ApplicationId;
    }

    public void setApplicationId(String applicationId) {
        ApplicationId = applicationId;
    }

    public String getSchemeName() {
        return SchemeName;
    }

    public void setSchemeName(String schemeName) {
        SchemeName = schemeName;
    }

    public String getSchemeCatagory() {
        return SchemeCatagory;
    }

    public void setSchemeCatagory(String schemeCatagory) {
        SchemeCatagory = schemeCatagory;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getQuery() {
        return Query;
    }

    public void setQuery(String query) {
        Query = query;
    }
}
