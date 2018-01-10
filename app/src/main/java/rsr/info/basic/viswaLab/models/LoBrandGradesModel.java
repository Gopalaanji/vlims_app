package rsr.info.basic.viswaLab.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${GIRI} on 08-01-2018.
 */

public class LoBrandGradesModel {

    @SerializedName("RefOilParameterId")
    private String RefOilParameterId;

    @SerializedName("LOSupplierBrand")
    private String LOSupplierBrand;

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
