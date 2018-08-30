package dev.info.basic.viswaLab.AnalysisReportsPage.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Giri Thangellapally on 23-04-2018.
 */
public class PurifierEffyResponseModel {
    @SerializedName("PEDTestDataId")
    String pEDTestDataId;
    @SerializedName("Serial")
    String serial;
    @SerializedName("ShipName")
    String shipName;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @SerializedName("Grade")

    String grade;
    @SerializedName("Matrix")
    String matrix;
    @SerializedName("BunkerPortName")
    String bunkerPortName;
    @SerializedName("BunkerDate")
    String bunkerDate;
    @SerializedName("LocationCity")
    String locationCity;
    @SerializedName("Result")
    String result;

    public String getAdditionalTest() {
        return additionalTest;
    }

    public void setAdditionalTest(String additionalTest) {
        this.additionalTest = additionalTest;
    }

    @SerializedName("AdditionalTest")

    String additionalTest;

    public String getpEDTestDataId() {
        return pEDTestDataId;
    }

    public void setpEDTestDataId(String pEDTestDataId) {
        this.pEDTestDataId = pEDTestDataId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMatrix() {
        return matrix;
    }

    public void setMatrix(String matrix) {
        this.matrix = matrix;
    }

    public String getBunkerPortName() {
        return bunkerPortName;
    }

    public void setBunkerPortName(String bunkerPortName) {
        this.bunkerPortName = bunkerPortName;
    }

    public String getBunkerDate() {
        return bunkerDate;
    }

    public void setBunkerDate(String bunkerDate) {
        this.bunkerDate = bunkerDate;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    @SerializedName("CustomerName")
    String customerName;
    @SerializedName("CertificateNo")
    String certificateNo;
    @SerializedName("ReportDate")
    String reportDate;

}
