package dev.info.basic.viswaLab.AnalysisReportsPage.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Giri Thangellapally on 23-03-2018.
 */

public class AnalysisCLOModel {
    @SerializedName("UserId")
    String userId;
    @SerializedName("ShipName")
    String shipName;
    @SerializedName("ShipId")
    String shipId;
    @SerializedName("IMONumber")
    String iMONumber;
    @SerializedName("Grade")
    String grade;
    @SerializedName("TestDate")
    String testDate;
    @SerializedName("OilCondition")
    String oilCondition;
}
