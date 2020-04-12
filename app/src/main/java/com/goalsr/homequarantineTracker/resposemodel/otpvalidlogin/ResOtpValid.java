package com.goalsr.homequarantineTracker.resposemodel.otpvalidlogin;

public class ResOtpValid{
	private String msg;
	private ResOtpValidData data;
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setData(ResOtpValidData data){
		this.data = data;
	}

	public ResOtpValidData getData(){
		return data;
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
			"ResOtpValid{" + 
			"msg = '" + msg + '\'' + 
			",data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
