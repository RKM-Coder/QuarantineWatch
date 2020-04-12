package com.goalsr.homequarantineTracker.resposemodel.getPatientinfo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "patent_family")
public class ResPatientFamilyInfo {


	@PrimaryKey
	@NonNull
	private int CitizenFamilyPersonId;


	private String Building;

	private String Email;

	private int districtCode;

	private double latitude;

	private int CitizenID;

	private boolean fever;


	private String Name;

	private int GenID;

	private String DOA;

	private String Street;

	private boolean BreathingProblem;

	private int Age;

	private double longitude;

	private String DateQurantine;

	private String PoArrival;

	private boolean Hypertension;
	private int talukCode;

	private String HNo;

	private boolean Diarrhea;

	private boolean CoughSourThroat;

	private String City;


	private String Mobile;

	private boolean HIV;


	private String POOrigin;

	private String Additional;

	private int uRoleBy;

	private String CreatedDate;


	private int RelationId;

	private boolean Diabetes;

	private boolean HeartIssue;


	public int getCitizenFamilyPersonId() {
		return CitizenFamilyPersonId;
	}

	public void setCitizenFamilyPersonId(int citizenFamilyPersonId) {
		CitizenFamilyPersonId = citizenFamilyPersonId;
	}

	public String getBuilding() {
		return Building;
	}

	public void setBuilding(String building) {
		Building = building;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public int getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(int districtCode) {
		this.districtCode = districtCode;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public int getCitizenID() {
		return CitizenID;
	}

	public void setCitizenID(int citizenID) {
		CitizenID = citizenID;
	}

	public boolean isFever() {
		return fever;
	}

	public void setFever(boolean fever) {
		this.fever = fever;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getGenID() {
		return GenID;
	}

	public void setGenID(int genID) {
		GenID = genID;
	}

	public String getDOA() {
		return DOA;
	}

	public void setDOA(String DOA) {
		this.DOA = DOA;
	}

	public String getStreet() {
		return Street;
	}

	public void setStreet(String street) {
		Street = street;
	}

	public boolean isBreathingProblem() {
		return BreathingProblem;
	}

	public void setBreathingProblem(boolean breathingProblem) {
		BreathingProblem = breathingProblem;
	}

	public int getAge() {
		return Age;
	}

	public void setAge(int age) {
		Age = age;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getDateQurantine() {
		return DateQurantine;
	}

	public void setDateQurantine(String dateQurantine) {
		DateQurantine = dateQurantine;
	}

	public String getPoArrival() {
		return PoArrival;
	}

	public void setPoArrival(String poArrival) {
		PoArrival = poArrival;
	}

	public boolean isHypertension() {
		return Hypertension;
	}

	public void setHypertension(boolean hypertension) {
		Hypertension = hypertension;
	}

	public int getTalukCode() {
		return talukCode;
	}

	public void setTalukCode(int talukCode) {
		this.talukCode = talukCode;
	}

	public String getHNo() {
		return HNo;
	}

	public void setHNo(String HNo) {
		this.HNo = HNo;
	}

	public boolean isDiarrhea() {
		return Diarrhea;
	}

	public void setDiarrhea(boolean diarrhea) {
		Diarrhea = diarrhea;
	}

	public boolean isCoughSourThroat() {
		return CoughSourThroat;
	}

	public void setCoughSourThroat(boolean coughSourThroat) {
		CoughSourThroat = coughSourThroat;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public boolean isHIV() {
		return HIV;
	}

	public void setHIV(boolean HIV) {
		this.HIV = HIV;
	}

	public String getPOOrigin() {
		return POOrigin;
	}

	public void setPOOrigin(String POOrigin) {
		this.POOrigin = POOrigin;
	}

	public String getAdditional() {
		return Additional;
	}

	public void setAdditional(String additional) {
		Additional = additional;
	}

	public int getURoleBy() {
		return uRoleBy;
	}

	public void setURoleBy(int uRoleBy) {
		this.uRoleBy = uRoleBy;
	}

	public String getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}

	public int getRelationId() {
		return RelationId;
	}

	public void setRelationId(int relationId) {
		RelationId = relationId;
	}

	public boolean isDiabetes() {
		return Diabetes;
	}

	public void setDiabetes(boolean diabetes) {
		Diabetes = diabetes;
	}

	public boolean isHeartIssue() {
		return HeartIssue;
	}

	public void setHeartIssue(boolean heartIssue) {
		HeartIssue = heartIssue;
	}
}