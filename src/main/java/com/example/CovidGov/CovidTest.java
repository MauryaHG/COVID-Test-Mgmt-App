package com.example.CovidGov;

import com.example.CovidGov.enums.EnumCovidTest;

public class CovidTest {
    private EnumCovidTest testType;

    public EnumCovidTest getTestType() {
        return testType;
    }

    public void setTestType(EnumCovidTest testType) {
        this.testType = testType;
    }

    public CovidTest(EnumCovidTest testType) {
        this.testType = testType;
    }



}
