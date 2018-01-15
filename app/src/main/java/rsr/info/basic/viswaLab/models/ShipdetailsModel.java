package rsr.info.basic.viswaLab.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${GIRI} on 08-01-2018.
 */

public class ShipdetailsModel {

    @SerializedName("ShipId")
    private int ShipId;
    @SerializedName("ShipName")
    private String ShipName;

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
}
