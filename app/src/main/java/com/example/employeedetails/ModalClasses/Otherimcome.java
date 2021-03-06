package com.example.employeedetails.ModalClasses;

import java.io.Serializable;

public class Otherimcome implements Serializable {
    int interestOnSavings;
    int otherIncome;

    public Otherimcome(int interestOnSavings, int otherIncome) {
        this.interestOnSavings = interestOnSavings;
        this.otherIncome = otherIncome;
    }

    public int getInterestOnSavings() {
        return interestOnSavings;
    }

    public void setInterestOnSavings(int interestOnSavings) {
        this.interestOnSavings = interestOnSavings;
    }

    public void setOtherIncome(int otherIncome) {
        this.otherIncome = otherIncome;
    }

    public int getOtherIncome() {
        return otherIncome;
    }
    public int getTotalOtherIncome(){
        return getOtherIncome()+getInterestOnSavings();
    }
}
