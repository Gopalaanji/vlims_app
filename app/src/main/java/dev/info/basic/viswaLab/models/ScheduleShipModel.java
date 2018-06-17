package dev.info.basic.viswaLab.models;

import com.google.gson.annotations.SerializedName;

public class ScheduleShipModel {
    @SerializedName("Shipid")
    private int ShipId;

    public int getShipId() {
        return ShipId;
    }

    public void setShipId(int shipId) {
        ShipId = shipId;
    }

    public String getShipName() {
        return ShipName;
    }

    public void setShipName(String shipName) {
        ShipName = shipName;
    }

    @SerializedName("Shipname")

    private String ShipName;
}
