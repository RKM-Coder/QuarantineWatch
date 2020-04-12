package com.goalsr.homequarantineTracker.resposemodel.getotp;

public class ResGetOtp{
	private String msg;
	private GetOtpData data;
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setData(GetOtpData data){
		this.data = data;
	}

	public GetOtpData getData(){
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
			"ResGetOtp{" + 
			"msg = '" + msg + '\'' + 
			",data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
