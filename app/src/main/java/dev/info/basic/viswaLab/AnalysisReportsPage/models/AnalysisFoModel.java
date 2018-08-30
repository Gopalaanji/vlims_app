package dev.info.basic.viswaLab.AnalysisReportsPage.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Giri Thangellapally on 23-03-2018.
 */

public class AnalysisFoModel {
    @SerializedName("UserId")
    String userId;
    @SerializedName("ShipName")
    String shipName;
    @SerializedName("ShipId")
    String shipId;
    @SerializedName("Serial")
    String serial;
    @SerializedName("ReportDate")
    String reportDate;

    public int getvlims_overall_fuel_condition() {
        return vlims_overall_fuel_condition;
    }

    public void setvLIMS_OVERALL_FUEL_CONDITION(int vlims_overall_fuel_condition) {
        this.vlims_overall_fuel_condition = vlims_overall_fuel_condition;
    }

    @SerializedName("Spec On / Off")
    String sepc;
    @SerializedName("TestDate")
    String testDate;
    @SerializedName("vlims_overall_fuel_condition")
    int vlims_overall_fuel_condition;

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public String getOilCondition() {
        return oilCondition;
    }

    public void setOilCondition(String oilCondition) {
        this.oilCondition = oilCondition;
    }

    @SerializedName("OilCondition")
    String oilCondition;

    public String getSepc() {
        return sepc;
    }

    public void setSepc(String sepc) {
        this.sepc = sepc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getiMONumber() {
        return iMONumber;
    }

    public void setiMONumber(String iMONumber) {
        this.iMONumber = iMONumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getBunkerPort() {
        return bunkerPort;
    }

    public void setBunkerPort(String bunkerPort) {
        this.bunkerPort = bunkerPort;
    }

    public String getBunkerDate() {
        return bunkerDate;
    }

    public void setBunkerDate(String bunkerDate) {
        this.bunkerDate = bunkerDate;
    }

    @SerializedName("IMONumber")
    String iMONumber;
    @SerializedName("Grade")
    String grade;
    @SerializedName("BunkerPort")
    String bunkerPort;
    @SerializedName("BunkerDate")
    String bunkerDate;


}
