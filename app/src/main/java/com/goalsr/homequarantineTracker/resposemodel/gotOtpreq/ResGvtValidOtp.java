package com.goalsr.homequarantineTracker.resposemodel.gotOtpreq;

public class ResGvtValidOtp{
	private int statuscode;
	private boolean Status;
	private String ExceptionMessage;
	private int RoleId;
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

	public int getRoleId() {
		return RoleId;
	}

	public void setRoleId(int roleId) {
		RoleId = roleId;
	}

	public String getMessageToDisplay() {
		return MessageToDisplay;
	}

	public void setMessageToDisplay(String messageToDisplay) {
		MessageToDisplay = messageToDisplay;
	}
}
