package dev.info.basic.viswaLab.utils;

import dev.info.basic.viswaLab.ApiInterfaces.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RetrofitInterface {
    @FormUrlEncoded
    @POST("VL_FOReports_Download")
    Call<ResponseBody> downloadFileByUrl_FO(@Field("username") String username,@Field("password") String password,@Field("serialNo") String serialNo);
 @FormUrlEncoded
    @POST("VL_LOReports_Download")
    Call<ResponseBody> downloadFileByUrl_LO(@Field("username") String username,@Field("password") String password,@Field("serialNo") String serialNo);

@FormUrlEncoded
    @POST("VL_CLOReports_Download")
    Call<ResponseBody> downloadFileByUrl_CLO(@Field("username") String username,@Field("password") String password,@Field("serialNo") String serialNo);
@FormUrlEncoded
    @POST("VL_ADDLReports_Download")
    Call<ResponseBody> downloadFileByUrl_ADDL(@Field("username") String username,@Field("password") String password,@Field("serialNo") String serialNo);
@FormUrlEncoded
    @POST("VL_POMPReports_Download")
    Call<ResponseBody> downloadFileByUrl_POMP_AR(@Field("username") String username,@Field("password") String password,@Field("serialNo") String serialNo);
@FormUrlEncoded
    @POST("VL_PEDReports_Download")
    Call<ResponseBody> downloadFileByUrl_PED_AR(@Field("username") String username,@Field("password") String password,@Field("serialNo") String serialNo);


@FormUrlEncoded
    @POST("VL_TechUpdates_Download")
    Call<ResponseBody> downloadFileByUrl_TU(@Field("username") String username,@Field("password") String password,@Field("FileName") String fileName);

}
