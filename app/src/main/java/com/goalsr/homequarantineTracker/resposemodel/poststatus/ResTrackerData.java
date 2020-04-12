package com.goalsr.homequarantineTracker.resposemodel.poststatus;

import java.util.List;

public class ResTrackerData {
	private List<TrackResponseItem> trackResponse;

	public void setTrackResponse(List<TrackResponseItem> trackResponse){
		this.trackResponse = trackResponse;
	}

	public List<TrackResponseItem> getTrackResponse(){
		return trackResponse;
	}

	@Override
 	public String toString(){
		return 
			"ResTrackerData{" +
			"trackResponse = '" + trackResponse + '\'' + 
			"}";
		}
}