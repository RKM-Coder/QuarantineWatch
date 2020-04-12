package com.goalsr.homequarantineTracker.resposemodel.emergency;

public class ReqBodyEmergency {
	private String quarantineStatus;
	private String latitude;
	private String userId;
	private String longitude;

	public void setQuarantineStatus(String quarantineStatus){
		this.quarantineStatus = quarantineStatus;
	}

	public String getQuarantineStatus(){
		return quarantineStatus;
	}

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}

	@Override
 	public String toString(){
		return 
			"ReqBodyEmergency{" +
			"quarantineStatus = '" + quarantineStatus + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",userId = '" + userId + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}
}
