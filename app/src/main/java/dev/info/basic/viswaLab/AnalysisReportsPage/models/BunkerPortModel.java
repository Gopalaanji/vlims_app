package dev.info.basic.viswaLab.AnalysisReportsPage.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Giri Thangellapally on 23-04-2018.
 */
public class BunkerPortModel {
    @SerializedName("VLIMS_BUNKER_PORT_ID")
    int vlims_bunker_port_id;
    @SerializedName("VLIMS_BUNKER_PORT_NAME")
    String vlims_bunker_port_name;

    public int getVlims_bunker_port_id() {
        return vlims_bunker_port_id;
    }

    public void setVlims_bunker_port_id(int vlims_bunker_port_id) {
        this.vlims_bunker_port_id = vlims_bunker_port_id;
    }

    public String getVlims_bunker_port_name() {
        return vlims_bunker_port_name;
    }

    public void setVlims_bunker_port_name(String vlims_bunker_port_name) {
        this.vlims_bunker_port_name = vlims_bunker_port_name;
    }
}
