package com.goalsr.homequarantineTracker.resposemodel.poststatus;

public class ResTracker{
	private String msg;
	private ResTrackerData data;
	private String status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setData(ResTrackerData data){
		this.data = data;
	}

	public ResTrackerData getData(){
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
			"ResTracker{" + 
			"msg = '" + msg + '\'' + 
			",data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
