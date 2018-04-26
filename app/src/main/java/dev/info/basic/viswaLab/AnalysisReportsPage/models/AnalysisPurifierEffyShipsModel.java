package dev.info.basic.viswaLab.AnalysisReportsPage.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Giri Thangellapally on 23-04-2018.
 */
public class AnalysisPurifierEffyShipsModel {
    @SerializedName("VLIMS_SHIP_ID")
    int vlims_ship_id;
    @SerializedName("VLIMS_SHIP_NAME")
    String vlims_ship_name;
    @SerializedName("VLIMS_SHIP_IMO_NUMBER")
    String vlims_ship_imo_number;

    public int getVlims_ship_id() {
        return vlims_ship_id;
    }

    public void setVlims_ship_id(int vlims_ship_id) {
        this.vlims_ship_id = vlims_ship_id;
    }

    public String getVlims_ship_name() {
        return vlims_ship_name;
    }

    public void setVlims_ship_name(String vlims_ship_name) {
        this.vlims_ship_name = vlims_ship_name;
    }

    public String getVlims_ship_imo_number() {
        return vlims_ship_imo_number;
    }

    public void setVlims_ship_imo_number(String vlims_ship_imo_number) {
        this.vlims_ship_imo_number = vlims_ship_imo_number;
    }
}
