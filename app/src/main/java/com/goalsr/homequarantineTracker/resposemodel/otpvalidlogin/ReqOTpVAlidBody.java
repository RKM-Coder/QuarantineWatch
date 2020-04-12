package com.goalsr.homequarantineTracker.resposemodel.otpvalidlogin;

public class ReqOTpVAlidBody{
	private String pnsToken;
	private String otp;
	private String deviceId;
	private String phoneNo;

	public void setPnsToken(String pnsToken){
		this.pnsToken = pnsToken;
	}

	public String getPnsToken(){
		return pnsToken;
	}

	public void setOtp(String otp){
		this.otp = otp;
	}

	public String getOtp(){
		return otp;
	}

	public void setDeviceId(String deviceId){
		this.deviceId = deviceId;
	}

	public String getDeviceId(){
		return deviceId;
	}

	public void setPhoneNo(String phoneNo){
		this.phoneNo = phoneNo;
	}

	public String getPhoneNo(){
		return phoneNo;
	}

	@Override
 	public String toString(){
		return 
			"ReqOTpVAlidBody{" + 
			"pnsToken = '" + pnsToken + '\'' + 
			",otp = '" + otp + '\'' + 
			",deviceId = '" + deviceId + '\'' + 
			",phoneNo = '" + phoneNo + '\'' + 
			"}";
		}
}
