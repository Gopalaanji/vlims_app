package dev.info.basic.viswaLab.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${GIRI} on 08-02-2018.
 */

public class AdhocReportsModel {
    @SerializedName("serialno")
    String SERIAL_NO;
    @SerializedName("teststatus")
    String TEST_STATUS;
    @SerializedName("testdate")
    String TEST_DATE;
    @SerializedName("testname")
    String TEST_NAME;
    @SerializedName("shipname")
    String SHIP_NAME;
    @SerializedName("imonumber")
    String SHIP_IMO_NUMBER;
    @SerializedName("link")
    String LINK;

    public String getSERIAL_NO() {
        return SERIAL_NO;
    }

    public void setSERIAL_NO(String SERIAL_NO) {
        this.SERIAL_NO = SERIAL_NO;
    }

    public String getTEST_STATUS() {
        return TEST_STATUS;
    }

    public void setTEST_STATUS(String TEST_STATUS) {
        this.TEST_STATUS = TEST_STATUS;
    }

    public String getTEST_DATE() {
        return TEST_DATE;
    }

    public void setTEST_DATE(String TEST_DATE) {
        this.TEST_DATE = TEST_DATE;
    }

    public String getTEST_NAME() {
        return TEST_NAME;
    }

    public void setTEST_NAME(String TEST_NAME) {
        this.TEST_NAME = TEST_NAME;
    }

    public String getSHIP_NAME() {
        return SHIP_NAME;
    }

    public void setSHIP_NAME(String SHIP_NAME) {
        this.SHIP_NAME = SHIP_NAME;
    }

    public String getSHIP_IMO_NUMBER() {
        return SHIP_IMO_NUMBER;
    }

    public void setSHIP_IMO_NUMBER(String SHIP_IMO_NUMBER) {
        this.SHIP_IMO_NUMBER = SHIP_IMO_NUMBER;
    }

    public String getLINK() {
        return LINK;
    }

    public void setLINK(String LINK) {
        this.LINK = LINK;
    }


}
