package dev.info.basic.viswaLab.AnalysisReportsPage.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PRAVEEN PAGINDLA on 06-May-18.
 */

public class AnalysisReportDensityDataModel {
    @SerializedName("FuelSampleId")
    String FuelSampleId;
    @SerializedName("Serial")
    String Serial;
    @SerializedName("ShipName")
    String ShipName;
    @SerializedName("Grade")
    String Grade;
    @SerializedName("GradeMatrix")
    String GradeMatrix;
    @SerializedName("BunkerPort")
    String BunkerPort;
    @SerializedName("BunkerDate")
    String BunkerDate;
    @SerializedName("TestDate")
    String TestDate;

    @SerializedName("TestedDen")
    String TestedDen;
    @SerializedName("DensityDifference")
    String DensityDifference;

    @SerializedName("DifferenceTonnes")
    String DifferenceTonnes;
    @SerializedName("Supplier")
    String Supplier;
    @SerializedName("Barge")
    String Barge;
    @SerializedName("QtyReceived")
    String QtyReceived;


    @SerializedName("RecDen")
    String RecDen;

    public String getFuelSampleId() {
        return FuelSampleId;
    }

    public void setFuelSampleId(String fuelSampleId) {
        FuelSampleId = fuelSampleId;
    }

    public String getSerial() {
        return Serial;
    }

    public void setSerial(String serial) {
        Serial = serial;
    }

    public String getShipName() {
        return ShipName;
    }

    public void setShipName(String shipName) {
        ShipName = shipName;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getGradeMatrix() {
        return GradeMatrix;
    }

    public void setGradeMatrix(String gradeMatrix) {
        GradeMatrix = gradeMatrix;
    }

    public String getBunkerPort() {
        return BunkerPort;
    }

    public void setBunkerPort(String bunkerPort) {
        BunkerPort = bunkerPort;
    }

    public String getBunkerDate() {
        return BunkerDate;
    }

    public void setBunkerDate(String bunkerDate) {
        BunkerDate = bunkerDate;
    }

    public String getTestDate() {
        return TestDate;
    }

    public void setTestDate(String testDate) {
        TestDate = testDate;
    }

    public String getTestedDen() {
        return TestedDen;
    }

    public void setTestedDen(String testedDen) {
        TestedDen = testedDen;
    }

    public String getDensityDifference() {
        return DensityDifference;
    }

    public void setDensityDifference(String densityDifference) {
        DensityDifference = densityDifference;
    }

    public String getDifferenceTonnes() {
        return DifferenceTonnes;
    }

    public void setDifferenceTonnes(String differenceTonnes) {
        DifferenceTonnes = differenceTonnes;
    }

    public String getSupplier() {
        return Supplier;
    }

    public void setSupplier(String supplier) {
        Supplier = supplier;
    }

    public String getBarge() {
        return Barge;
    }

    public void setBarge(String barge) {
        Barge = barge;
    }

    public String getQtyReceived() {
        return QtyReceived;
    }

    public void setQtyReceived(String qtyReceived) {
        QtyReceived = qtyReceived;
    }

    public String getRecDen() {
        return RecDen;
    }

    public void setRecDen(String recDen) {
        RecDen = recDen;
    }

    public String getRecVis() {
        return RecVis;
    }

    public void setRecVis(String recVis) {
        RecVis = recVis;
    }

    public String getRecH2O() {
        return RecH2O;
    }

    public void setRecH2O(String recH2O) {
        RecH2O = recH2O;
    }

    public String getRecSul() {
        return RecSul;
    }

    public void setRecSul(String recSul) {
        RecSul = recSul;
    }

    public String getDEN() {
        return DEN;
    }

    public void setDEN(String DEN) {
        this.DEN = DEN;
    }

    public String getAPIGRAV() {
        return APIGRAV;
    }

    public void setAPIGRAV(String APIGRAV) {
        this.APIGRAV = APIGRAV;
    }

    public String getVIS40() {
        return VIS40;
    }

    public void setVIS40(String VIS40) {
        this.VIS40 = VIS40;
    }

    public String getVIS50() {
        return VIS50;
    }

    public void setVIS50(String VIS50) {
        this.VIS50 = VIS50;
    }

    public String getVIS100() {
        return VIS100;
    }

    public void setVIS100(String VIS100) {
        this.VIS100 = VIS100;
    }

    @SerializedName("RecVis")

    String RecVis;
    @SerializedName("RecH2O")
    String RecH2O;
    @SerializedName("RecSul")
    String RecSul;

    @SerializedName("DEN")
    String DEN;
    @SerializedName("APIGRAV")
    String APIGRAV;
    @SerializedName("VIS40")
    String VIS40;
    @SerializedName("VIS50")
    String VIS50;
    @SerializedName("VIS100")
    String VIS100;

}
