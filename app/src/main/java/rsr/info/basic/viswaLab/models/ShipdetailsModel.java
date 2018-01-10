package rsr.info.basic.viswaLab.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${GIRI} on 08-01-2018.
 */

public class ShipdetailsModel {

    @SerializedName("ShipId")
    private String ShipId;
    @SerializedName("ShipName")
    private String ShipName;

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
}
