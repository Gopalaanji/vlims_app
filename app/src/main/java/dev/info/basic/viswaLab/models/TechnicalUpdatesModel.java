package dev.info.basic.viswaLab.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${GIRI} on 07-02-2018.
 */

public class TechnicalUpdatesModel {

    @SerializedName("articledate")
    String date;
    @SerializedName("heading")
    String heading;
    @SerializedName("link")
    String link;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
