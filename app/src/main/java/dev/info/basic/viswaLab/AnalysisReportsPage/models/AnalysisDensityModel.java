package dev.info.basic.viswaLab.AnalysisReportsPage.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Giri Thangellapally on 23-04-2018.
 */
public class AnalysisDensityModel {
    @SerializedName("ShipName")
    String shipName;
    @SerializedName("BunkerDate")
    String bunkerDate;

    @SerializedName("BunkerPort")
    String bunkerPort;
    @SerializedName("Grade")
    String grade;
    @SerializedName("GradeMatrix")
    String gradeMatrix;

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getBunkerDate() {
        return bunkerDate;
    }

    public void setBunkerDate(String bunkerDate) {
        this.bunkerDate = bunkerDate;
    }

    public String getBunkerPort() {
        return bunkerPort;
    }

    public void setBunkerPort(String bunkerPort) {
        this.bunkerPort = bunkerPort;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGradeMatrix() {
        return gradeMatrix;
    }

    public void setGradeMatrix(String gradeMatrix) {
        this.gradeMatrix = gradeMatrix;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getDifferenceTonnes() {
        return differenceTonnes;
    }

    public void setDifferenceTonnes(String differenceTonnes) {
        this.differenceTonnes = differenceTonnes;
    }

    public String getQtyReceived() {
        return qtyReceived;
    }

    public void setQtyReceived(String qtyReceived) {
        this.qtyReceived = qtyReceived;
    }

    @SerializedName("Serial")
    String serial;
    @SerializedName("DifferenceTonnes")
    String differenceTonnes;
    @SerializedName("QtyReceived")
    String qtyReceived;

}
