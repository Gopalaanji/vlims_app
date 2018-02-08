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
    void GetAdhocReports(@Field("userID") String userid,Callback<JsonObject> callback);

}