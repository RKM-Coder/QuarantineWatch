package com.goalsr.homequarantineTracker.resposemodel.otpvalidGovt;

import com.goalsr.homequarantineTracker.resposemodel.SecurityObject;

public class ReqOtpValidGvt{
	private String MobileNo;
	private int Role;
	private String OTP;

	private SecurityObject pSecurity;

	public String getMobileNo() {
		return MobileNo;
	}

	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}

	public int getRole() {
		return Role;
	}

	public void setRole(int role) {
		Role = role;
	}

	public String getOTP() {
		return OTP;
	}

	public void setOTP(String OTP) {
		this.OTP = OTP;
	}

	public SecurityObject getpSecurity() {
		return pSecurity;
	}

	public void setpSecurity(SecurityObject pSecurity) {
		this.pSecurity = pSecurity;
	}
}
