package dev.info.basic.viswaLab.ApiInterfaces;

import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by RSR on 21-07-2017.
 */

public interface ApiInterface {

    public static String HeadUrl = "http://173.11.229.171/VLIMSAPP/GETuser.asmx";

    @FormUrlEncoded
    @POST("/CheckUserLogin")
    void GetUser(@Field("userName") String userName, @Field("password") String password, Callback<JsonObject> callback);

    @FormUrlEncoded
    @POST("/GetUserShipDetails")
    void GetUserShipDetails(@Field("userId") String userName, Callback<JsonObject> callback);

    @FormUrlEncoded
    @POST("/GetEquipementByShipDetails")
    void GetEquipementDataByShipDetails(@Field("shipID") String shipID, Callback<JsonObject> callback);

    @FormUrlEncoded
    @POST("/GetSupplierBrandByEquipment")
    void GetSupplierBrandDataByEquipmentDetails(@Field("shipID") String shipID, @Field("EquipmentId") String EquipmentId, Callback<JsonObject> callback);

    @FormUrlEncoded
    @POST("/GetDataForReport")
    void GetDataForReport(@Field("shipId") int shipID, @Field("ImoNumber") String ImoNumber, Callback<JsonObject> callback);

    @FormUrlEncoded
    @POST("/LO_SN_Search")
    void GetSrDataForReport(@Field("userid") String userid, @Field("serialNumber") String serialNumber, Callback<JsonObject> callback);

    @FormUrlEncoded
    @POST("/GetSampleSummaryReport")
    void GetSampleSummaryReport(@Field("userid") String userid, Callback<JsonObject> callback);

    @FormUrlEncoded
    @POST("/GetSampleReportForUserByShipIdAndImoId")
    void GetSampleReportForUserByShipIdAndImoId(@Field("userId") String userid, @Field("shipId") int shipId, @Field("ImoNumber") String ImoNumber, Callback<JsonObject> callback);

    @FormUrlEncoded
    @POST("/LO_Shedule")
    void GetScheduleAlertsDataByShipIdAndImoId(@Field("userId") String userid, @Field("shipId") int shipId, @Field("ImoNumber") String ImoNumber, Callback<JsonObject> callback);

    @GET("/VL_TechnicalUpdates")
    void GetTechnicalUpdates(Callback<JsonObject> callback);

    @FormUrlEncoded
    @POST("/VL_AdhocReports")
    void GetAdhocReports(@Field("userID") String userid, Callback<JsonObject> callback);

    @FormUrlEncoded
    @POST("/ForgotPassword")
    void GetForgot(@Field("username") String username, @Field("mailid") String mailid, @Field("mobilenumber") String mobilenumber, Callback<JsonObject> callback);

    //ANALYSYS REPORTS

    //FO ships spinner
    @FormUrlEncoded
    @POST("/GetVL_FO_ActiveReports")
    void GetFuelOilReportsAnalysisReportsShips(@Field("userid") String userid, Callback<JsonObject> callback);

    //CLO ships spinner
    @FormUrlEncoded
    @POST("/GetVL_CLO_ActiveReports")
    void GetCylinderOilReportsAnalysisReportsShips(@Field("userid") String userid, Callback<JsonObject> callback);

    //POMP ships spinner
    @FormUrlEncoded
    @POST("/GetVL_POMP_ActiveReports")
    void GetPOMPAnalysisReportsShips(@Field("userid") String userid, Callback<JsonObject> callback);

    //LO ships spinner
    @FormUrlEncoded
    @POST("/GetVL_LO_ActiveReports")
    void GetLubeOilAnalysisReportsShips(@Field("userid") String userid, Callback<JsonObject> callback);

    @FormUrlEncoded
    @POST("/GetVL_FO_Reports")
    void GetFuelOilReportsAnalysisReports(@Field("userid") String userid, @Field("shipid") int shipid, @Field("imonumber") String imonumber, Callback<JsonObject> callback);

    @FormUrlEncoded
    @POST("/GetVL_CLO_Reports")
    void GetCylinderOilReportsAnalysisReports(@Field("userid") String userid, @Field("shipid") int shipid, @Field("imonumber") String imonumber, Callback<JsonObject> callback);


    @FormUrlEncoded
    @POST("/GetVL_POMP_Reports")
    void GetPompsReportAnalysisReports(@Field("userid") String userid, @Field("shipid") int shipid, @Field("imonumber") String imonumber, Callback<JsonObject> callback);

    //CAUTION ALERTS

    //FO ships spinner
    @FormUrlEncoded
    @POST("/GetVL_FO_ActiveCautionAlerts")
    void GetFuelOilCOShips(@Field("userid") String userid, Callback<JsonObject> callback);

    //LO ships spinner
    @FormUrlEncoded
    @POST("/GetVL_LO_ActiveCautionAlerts")
    void GetLOOilCOShips(@Field("userid") String userid, Callback<JsonObject> callback);

    //CLO ships spinner
    @FormUrlEncoded
    @POST("/GetVL_CLO_ActiveCautionAlerts")
    void GetCylinderOilOilCOShips(@Field("userid") String userid, Callback<JsonObject> callback);

    @FormUrlEncoded
    @POST("/GetVL_FO_CautionAlerts")
    void GetFuelOilReportsCautionAlert(@Field("userid") String userid, @Field("shipid") int shipid, @Field("imonumber") String imonumber, Callback<JsonObject> callback);

    @FormUrlEncoded
    @POST("/GetVL_CLO_CautionAlerts")
    void GetCylinderOilReportsCautionAlert(@Field("userid") String userid, @Field("shipid") int shipid, @Field("imonumber") String imonumber, Callback<JsonObject> callback);

}