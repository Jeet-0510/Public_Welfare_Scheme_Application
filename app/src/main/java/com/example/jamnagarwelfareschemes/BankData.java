package com.example.jamnagarwelfareschemes;

public class BankData {

    String BankName, Branch, IFSC, AccountNo, RTAccountNo;

    public BankData() {
    }

    public BankData(String bankName, String branch, String IFSC, String accountNo, String RTAccountNo) {
        BankName = bankName;
        Branch = branch;
        this.IFSC = IFSC;
        AccountNo = accountNo;
        this.RTAccountNo = RTAccountNo;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getIFSC() {
        return IFSC;
    }

    public void setIFSC(String IFSC) {
        this.IFSC = IFSC;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String accountNo) {
        AccountNo = accountNo;
    }

    public String getRTAccountNo() {
        return RTAccountNo;
    }

    public void setRTAccountNo(String RTAccountNo) {
        this.RTAccountNo = RTAccountNo;
    }
}
