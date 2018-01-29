package dev.info.basic.viswaLab.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${GIRI} on 09-01-2018.
 */

public class ReportDataModel {

    @SerializedName("LubeOilId")
    private String LubeOilId;

    @SerializedName("Serial")
    private String Serial;

    @SerializedName("ShipName")
    private String ShipName;

    @SerializedName("LOEquipmentId")
    private String LOEquipmentId;

    @SerializedName("OilCondition")
    private String OilCondition;

    @SerializedName("Equipment")
    private String Equipment;

    @SerializedName("TestDate")
    private String TestDate;

    public String getLubeOilId() {
        return LubeOilId;
    }

    public void setLubeOilId(String lubeOilId) {
        LubeOilId = lubeOilId;
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

    public String getLOEquipmentId() {
        return LOEquipmentId;
    }

    public void setLOEquipmentId(String LOEquipmentId) {
        this.LOEquipmentId = LOEquipmentId;
    }

    public String getOilCondition() {
        return OilCondition;
    }

    public void setOilCondition(String oilCondition) {
        OilCondition = oilCondition;
    }

    public String getEquipment() {
        return Equipment;
    }

    public void setEquipment(String equipment) {
        Equipment = equipment;
    }

    public String getTestDate() {
        return TestDate;
    }

    public void setTestDate(String testDate) {
        TestDate = testDate;
    }
}
