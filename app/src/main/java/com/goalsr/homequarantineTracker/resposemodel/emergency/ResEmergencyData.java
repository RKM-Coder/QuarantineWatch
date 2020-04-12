package com.goalsr.homequarantineTracker.resposemodel.emergency;

public class ResEmergencyData {
	private String id;
	private String message;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"ResEmergencyData{" +
			"id = '" + id + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}
