package dev.info.basic.viswaLab.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${GIRI} on 28-01-2018.
 */

public class ScheduleAlertModel {
    @SerializedName("ShipId")
    String ShipId;
    @SerializedName("ShipName")
    String ShipName;
    @SerializedName("LOEquipmentId")
    String LOEquipmentId;
    @SerializedName("OilCondition")
    String OilCondition;
    @SerializedName("Equipment")
    String Equipment;
    @SerializedName("SheduleDate")
    String SheduleDate;

    public String getShipId() {
        return ShipId;
    }

    public void setShipId(String shipId) {
        ShipId = shipId;
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

    public String getSheduleDate() {
        return SheduleDate;
    }

    public void setSheduleDate(String sheduleDate) {
        SheduleDate = sheduleDate;
    }
}
