package dev.info.basic.viswaLab.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${GIRI} on 08-01-2018.
 */

public class LOEquipmentDetailsModel {

    @SerializedName("LOEquipmentId")
    private String LOEquipmentId;

    @SerializedName("LOEquipmentDescription")
    private String LOEquipmentDescription;

    @SerializedName("RefOilParameterId")
    public String RefOilParameterId;

    @SerializedName("LOSupplierBrand")
    public String LOSupplierBrand;

    public String getLOEquipmentId() {
        return LOEquipmentId;
    }

    public void setLOEquipmentId(String LOEquipmentId) {
        this.LOEquipmentId = LOEquipmentId;
    }

    public String getLOEquipmentDescription() {
        return LOEquipmentDescription;
    }

    public void setLOEquipmentDescription(String LOEquipmentDescription) {
        this.LOEquipmentDescription = LOEquipmentDescription;
    }

    public String getRefOilParameterId() {
        return RefOilParameterId;
    }

    public void setRefOilParameterId(String refOilParameterId) {
        RefOilParameterId = refOilParameterId;
    }

    public String getLOSupplierBrand() {
        return LOSupplierBrand;
    }

    public void setLOSupplierBrand(String LOSupplierBrand) {
        this.LOSupplierBrand = LOSupplierBrand;
    }
}
