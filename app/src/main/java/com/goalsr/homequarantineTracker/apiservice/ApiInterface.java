package com.goalsr.homequarantineTracker.apiservice;

import com.goalsr.homequarantineTracker.resposemodel.ReqGvtPatientFamillySymptom;
import com.goalsr.homequarantineTracker.resposemodel.ReqGvtPatientSymptom;
import com.goalsr.homequarantineTracker.resposemodel.ReqImageChunk;
import com.goalsr.homequarantineTracker.resposemodel.ReqPAtientInfoByAdmin;
import com.goalsr.homequarantineTracker.resposemodel.ResImage;
import com.goalsr.homequarantineTracker.resposemodel.ResSymtomChecker;
import com.goalsr.homequarantineTracker.resposemodel.emergency.ReqEmegency;
import com.goalsr.homequarantineTracker.resposemodel.emergency.ResEmergency;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ReqPatient;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ReqUpdatePatentIFamilynfo;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ReqUpdatePatentInfo;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientFamilyInfo;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfo;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfoByAdmin;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResUpdateInfo;
import com.goalsr.homequarantineTracker.resposemodel.gotOtpreq.ResGvtValidOtp;
import com.goalsr.homequarantineTracker.resposemodel.otpvalidGovt.ReqOtpValidGvt;
import com.goalsr.homequarantineTracker.resposemodel.otpvalidGovt.ResGvtValidOtpValid;
import com.goalsr.homequarantineTracker.resposemodel.poststatus.ReqStatus;
import com.goalsr.homequarantineTracker.resposemodel.poststatus.ResTracker;
import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/Values/ValidateOTP")
    Call<ResGvtValidOtpValid> makeOtpValidReq(
            @Body ReqOtpValidGvt request
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/Values/getCitizenMasterMobileOnly")
    Call<List<ResPatientInfo>> getPAtientInfo(
            @Body ReqPatient request
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/Values/getCitizenMasterByDistrict")
    Call<List<ResPatientInfoByAdmin>> getPAtientInfoListByAdmin(
            @Body ReqPAtientInfoByAdmin request
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/Values/getCitizenFamilyMember")
    Call<List<ResPatientFamilyInfo>> getPAtientFamilyInfo(
            @Body ReqPatient request
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/Values/UpdateCitizenSymptomNumber")
    Call<List<ResSymtomChecker>> sendpatientSymptom(
            @Body ReqGvtPatientSymptom request
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/Values/UpdateCitizenFamilyPersonSymptomNumber")
    Call<List<ResSymtomChecker>> sendpatientFamilySymptom(
            @Body ReqGvtPatientFamillySymptom request
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("BHOOMI/Fn_Insert_DOCCHNK/{PSTRUSERNAME}/{PSTRPASSWORD}/")
    Call<ResImage> sendprofileImage(
            @Body ReqImageChunk request,
            @Path ("PSTRUSERNAME") String PSTRUSERNAME,
            @Path ("PSTRPASSWORD") String PSTRPASSWORD
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/Values/InsertUpdateCitizenMaster")
    Call<List<ResUpdateInfo>> updatePAtientInfo(
            @Body ReqUpdatePatentInfo request
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("api/Values/InsertUpdateCitizenFamily")
    Call<List<ResUpdateInfo>> updatePAtientFamillyInfo(
            @Body ReqUpdatePatentIFamilynfo request
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("/quarantine-api/user/track")
    Call<ResTracker> makeTraker(
            @Body ReqStatus request
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("/quarantine-api/user/insertNotification")
    Call<ResEmergency> makeEmergencyTraker(
            @Body ReqEmegency request
    );



    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @PUT("/quarantine-api/user/updateUserQuarantineStatus")
    Call<JsonElement> makeEmergencyTrakerinside(
            @Body ReqEmegency request
    );

    /*GVT API
     * */

    @GET("api/Values/GetOTP")
    Call<ResGvtValidOtp> makeOtpReqGOVT(
            @Query("pPmobileNo") String mobileNo
    );


    /*@Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("/api/Values/ValidateOTP")
    Call<ResGvtValidOtpValid> makeOtpValidReq(
            @Body ReqOtpValidGvt request
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("/api/Values/getCitizenMasterMobileOnly")
    Call<List<ResPatientInfo>> getPAtientInfo(
            @Body ReqPatient request
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("/api/Values/getCitizenMasterByDistrict")
    Call<List<ResPatientInfoByAdmin>> getPAtientInfoListByAdmin(
            @Body ReqPAtientInfoByAdmin request
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("/api/Values/getCitizenFamilyMember")
    Call<List<ResPatientFamilyInfo>> getPAtientFamilyInfo(
            @Body ReqPatient request
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("/api/Values/UpdateCitizenSymptomNumber")
    Call<List<ResSymtomChecker>> sendpatientSymptom(
            @Body ReqGvtPatientSymptom request
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("/api/Values/UpdateCitizenFamilyPersonSymptomNumber")
    Call<List<ResSymtomChecker>> sendpatientFamilySymptom(
            @Body ReqGvtPatientFamillySymptom request
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("/RWS_QWATCH/BHOOMI/Fn_Insert_DOCCHNK/{PSTRUSERNAME}/{PSTRPASSWORD}/")
    Call<ResImage> sendprofileImage(
            @Body ReqImageChunk request,
            @Path ("PSTRUSERNAME") String PSTRUSERNAME,
            @Path ("PSTRPASSWORD") String PSTRPASSWORD
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("/api/Values/InsertUpdateCitizenMaster")
    Call<List<ResUpdateInfo>> updatePAtientInfo(
            @Body ReqUpdatePatentInfo request
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("/api/Values/InsertUpdateCitizenFamily")
    Call<List<ResUpdateInfo>> updatePAtientFamillyInfo(
            @Body ReqUpdatePatentIFamilynfo request
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("/quarantine-api/user/track")
    Call<ResTracker> makeTraker(
            @Body ReqStatus request
    );

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("/quarantine-api/user/insertNotification")
    Call<ResEmergency> makeEmergencyTraker(
            @Body ReqEmegency request
    );



    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @PUT("/quarantine-api/user/updateUserQuarantineStatus")
    Call<JsonElement> makeEmergencyTrakerinside(
            @Body ReqEmegency request
    );

    *//*GVT API
    * *//*

    @GET("/api/Values/GetOTP")
    Call<ResGvtValidOtp> makeOtpReqGOVT(
            @Query("pPmobileNo") String mobileNo
    );*/






}
