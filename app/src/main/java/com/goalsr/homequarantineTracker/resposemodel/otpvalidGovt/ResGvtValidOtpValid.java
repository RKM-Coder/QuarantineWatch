package com.goalsr.homequarantineTracker.resposemodel.otpvalidGovt;

public class ResGvtValidOtpValid {
	private int statuscode;
	private boolean Status;
	private String ExceptionMessage;
	private int UserId=0;
	private String MessageToDisplay;

	public int getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(int statuscode) {
		this.statuscode = statuscode;
	}

	public boolean isStatus() {
		return Status;
	}

	public void setStatus(boolean status) {
		Status = status;
	}

	public String getExceptionMessage() {
		return ExceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		ExceptionMessage = exceptionMessage;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public String getMessageToDisplay() {
		return MessageToDisplay;
	}

	public void setMessageToDisplay(String messageToDisplay) {
		MessageToDisplay = messageToDisplay;
	}
}
