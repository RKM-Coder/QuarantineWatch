package com.goalsr.homequarantineTracker.resposemodel.getotp;

public class GetOtpData {
	private String otp;
	private String message;
	private String userId;
	private String phoneNo;
	private String status;

	public void setOtp(String otp){
		this.otp = otp;
	}

	public String getOtp(){
		return otp;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setPhoneNo(String phoneNo){
		this.phoneNo = phoneNo;
	}

	public String getPhoneNo(){
		return phoneNo;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"GetOtpData{" +
			"otp = '" + otp + '\'' + 
			",message = '" + message + '\'' + 
			",userId = '" + userId + '\'' + 
			",phoneNo = '" + phoneNo + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
