package dev.info.basic.viswaLab.models;

import com.google.gson.annotations.SerializedName;

public class SampleStatsModel {

    @SerializedName("UserId")
    private String userId;
    @SerializedName("DataFor")
    private String dataFor;
    @SerializedName("CurrMonthCount")
    private String currMonthCount;
    @SerializedName("PrevMonthCount")
    private String prevMonthCount;
    @SerializedName("YTDCount")
    private String yTDCount;
    @SerializedName("Last12MonthsCount")
    private String last12MonthsCount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDataFor() {
        return dataFor;
    }

    public void setDataFor(String dataFor) {
        this.dataFor = dataFor;
    }

    public String getCurrMonthCount() {
        return currMonthCount;
    }

    public void setCurrMonthCount(String currMonthCount) {
        this.currMonthCount = currMonthCount;
    }

    public String getPrevMonthCount() {
        return prevMonthCount;
    }

    public void setPrevMonthCount(String prevMonthCount) {
        this.prevMonthCount = prevMonthCount;
    }

    public String getYTDCount() {
        return yTDCount;
    }

    public void setYTDCount(String yTDCount) {
        this.yTDCount = yTDCount;
    }

    public String getLast12MonthsCount() {
        return last12MonthsCount;
    }

    public void setLast12MonthsCount(String last12MonthsCount) {
        this.last12MonthsCount = last12MonthsCount;
    }

}
