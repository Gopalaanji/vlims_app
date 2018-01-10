package rsr.info.basic.viswaLab.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RSR on 13-04-2017.
 */

public class NewProjectDto {
    @SerializedName("project_id")
    private String project_id;
    @SerializedName("project_name")
    String project_name;
    @SerializedName("project_imageurl")
    String project_imageurl;
    @SerializedName("project_approvedby")
    String project_approvedby;
    @SerializedName("project_mobileview_url")
    String project_mobileview_url;
    @SerializedName("total")
    String total;
    @SerializedName("Available")
    String Available;

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    @SerializedName("Alloted")

    String Alloted;

    public String getProject_mobileview_url() {
        return project_mobileview_url;
    }

    public void setProject_mobileview_url(String project_mobileview_url) {
        this.project_mobileview_url = project_mobileview_url;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_imageurl() {
        return project_imageurl;
    }

    public void setProject_imageurl(String project_imageurl) {
        this.project_imageurl = project_imageurl;
    }

    public String getProject_approvedby() {
        return project_approvedby;
    }

    public void setProject_approvedby(String project_approvedby) {
        this.project_approvedby = project_approvedby;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAvailable() {
        return Available;
    }

    public void setAvailable(String available) {
        Available = available;
    }

    public String getAlloted() {
        return Alloted;
    }

    public void setAlloted(String alloted) {
        Alloted = alloted;
    }

}
