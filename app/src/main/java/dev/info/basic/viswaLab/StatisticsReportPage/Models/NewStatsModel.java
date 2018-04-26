package dev.info.basic.viswaLab.StatisticsReportPage.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Giri Thangellapally on 26-04-2018.
 */
public class NewStatsModel {
    @SerializedName("SampleType")
    private String sampleType;
    @SerializedName("SampleStatus")
    private String sampleStatus;
    @SerializedName("CurrentMonthCount")
    private String currentMonthCount;

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getSampleStatus() {
        return sampleStatus;
    }

    public void setSampleStatus(String sampleStatus) {
        this.sampleStatus = sampleStatus;
    }

    public String getCurrentMonthCount() {
        return currentMonthCount;
    }

    public void setCurrentMonthCount(String currentMonthCount) {
        this.currentMonthCount = currentMonthCount;
    }

    public String getPrevMonthCount() {
        return prevMonthCount;
    }

    public void setPrevMonthCount(String prevMonthCount) {
        this.prevMonthCount = prevMonthCount;
    }

    public String getyTDCount() {
        return yTDCount;
    }

    public void setyTDCount(String yTDCount) {
        this.yTDCount = yTDCount;
    }

    @SerializedName("PrevMonthCount")

    private String prevMonthCount;
    @SerializedName("YTDCount")
    private String yTDCount;
}
