package com.goalsr.homequarantineTracker.resposemodel.emergency;

public class ResEmergency{
	private String msg;
	private ResEmergencyData data;
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setData(ResEmergencyData data){
		this.data = data;
	}

	public ResEmergencyData getData(){
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
			"ResEmergency{" + 
			"msg = '" + msg + '\'' + 
			",data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
