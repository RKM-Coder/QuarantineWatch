package com.goalsr.homequarantineTracker.resposemodel.poststatus;

public class TrackResponseItem{
	private String id;
	private String message;
	private String localId;
	private String status;

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

	public void setLocalId(String localId){
		this.localId = localId;
	}

	public String getLocalId(){
		return localId;
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
			"TrackResponseItem{" + 
			"id = '" + id + '\'' + 
			",message = '" + message + '\'' + 
			",localId = '" + localId + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
