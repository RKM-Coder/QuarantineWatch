package com.goalsr.homequarantineTracker.resposemodel.getPatientinfo;


import com.google.gson.annotations.SerializedName;


public class ResPAtientBody{

	@SerializedName("Building")
	private Object building;

	@SerializedName("Email")
	private String email;

	@SerializedName("districtCode")
	private int districtCode;

	@SerializedName("latitude")
	private Object latitude;

	@SerializedName("CitizenID")
	private int citizenID;

	@SerializedName("fever")
	private boolean fever;

	@SerializedName("Name")
	private String name;

	@SerializedName("GenID")
	private Object genID;

	@SerializedName("DOA")
	private String dOA;

	@SerializedName("Street")
	private String street;

	@SerializedName("Cby")
	private int cby;

	@SerializedName("BreathingProblem")
	private boolean breathingProblem;

	@SerializedName("Age")
	private Object age;

	@SerializedName("longitude")
	private Object longitude;

	@SerializedName("DateQurantine")
	private String dateQurantine;

	@SerializedName("PoArrival")
	private Object poArrival;

	@SerializedName("Hypertension")
	private Object hypertension;

	@SerializedName("talukCode")
	private Object talukCode;

	@SerializedName("HNo")
	private Object hNo;

	@SerializedName("Diarrhea")
	private boolean diarrhea;

	@SerializedName("CoughSourThroat")
	private boolean coughSourThroat;

	@SerializedName("City")
	private String city;

	@SerializedName("Mobile")
	private String mobile;

	@SerializedName("HIV")
	private Object hIV;

	@SerializedName("POOrigin")
	private String pOOrigin;

	@SerializedName("Additional")
	private String additional;

	@SerializedName("uRoleBy")
	private int uRoleBy;

	@SerializedName("CreatedDate")
	private String createdDate;

	@SerializedName("Diabetes")
	private Object diabetes;

	@SerializedName("HeartIssue")
	private Object heartIssue;

	public void setBuilding(Object building){
		this.building = building;
	}

	public Object getBuilding(){
		return building;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setDistrictCode(int districtCode){
		this.districtCode = districtCode;
	}

	public int getDistrictCode(){
		return districtCode;
	}

	public void setLatitude(Object latitude){
		this.latitude = latitude;
	}

	public Object getLatitude(){
		return latitude;
	}

	public void setCitizenID(int citizenID){
		this.citizenID = citizenID;
	}

	public int getCitizenID(){
		return citizenID;
	}

	public void setFever(boolean fever){
		this.fever = fever;
	}

	public boolean isFever(){
		return fever;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setGenID(Object genID){
		this.genID = genID;
	}

	public Object getGenID(){
		return genID;
	}

	public void setDOA(String dOA){
		this.dOA = dOA;
	}

	public String getDOA(){
		return dOA;
	}

	public void setStreet(String street){
		this.street = street;
	}

	public String getStreet(){
		return street;
	}

	public void setCby(int cby){
		this.cby = cby;
	}

	public int getCby(){
		return cby;
	}

	public void setBreathingProblem(boolean breathingProblem){
		this.breathingProblem = breathingProblem;
	}

	public boolean isBreathingProblem(){
		return breathingProblem;
	}

	public void setAge(Object age){
		this.age = age;
	}

	public Object getAge(){
		return age;
	}

	public void setLongitude(Object longitude){
		this.longitude = longitude;
	}

	public Object getLongitude(){
		return longitude;
	}

	public void setDateQurantine(String dateQurantine){
		this.dateQurantine = dateQurantine;
	}

	public String getDateQurantine(){
		return dateQurantine;
	}

	public void setPoArrival(Object poArrival){
		this.poArrival = poArrival;
	}

	public Object getPoArrival(){
		return poArrival;
	}

	public void setHypertension(Object hypertension){
		this.hypertension = hypertension;
	}

	public Object getHypertension(){
		return hypertension;
	}

	public void setTalukCode(Object talukCode){
		this.talukCode = talukCode;
	}

	public Object getTalukCode(){
		return talukCode;
	}

	public void setHNo(Object hNo){
		this.hNo = hNo;
	}

	public Object getHNo(){
		return hNo;
	}

	public void setDiarrhea(boolean diarrhea){
		this.diarrhea = diarrhea;
	}

	public boolean isDiarrhea(){
		return diarrhea;
	}

	public void setCoughSourThroat(boolean coughSourThroat){
		this.coughSourThroat = coughSourThroat;
	}

	public boolean isCoughSourThroat(){
		return coughSourThroat;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setHIV(Object hIV){
		this.hIV = hIV;
	}

	public Object getHIV(){
		return hIV;
	}

	public void setPOOrigin(String pOOrigin){
		this.pOOrigin = pOOrigin;
	}

	public String getPOOrigin(){
		return pOOrigin;
	}

	public void setAdditional(String additional){
		this.additional = additional;
	}

	public String getAdditional(){
		return additional;
	}

	public void setURoleBy(int uRoleBy){
		this.uRoleBy = uRoleBy;
	}

	public int getURoleBy(){
		return uRoleBy;
	}

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	public void setDiabetes(Object diabetes){
		this.diabetes = diabetes;
	}

	public Object getDiabetes(){
		return diabetes;
	}

	public void setHeartIssue(Object heartIssue){
		this.heartIssue = heartIssue;
	}

	public Object getHeartIssue(){
		return heartIssue;
	}

	@Override
 	public String toString(){
		return 
			"ResPAtientBody{" + 
			"building = '" + building + '\'' + 
			",email = '" + email + '\'' + 
			",districtCode = '" + districtCode + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",citizenID = '" + citizenID + '\'' + 
			",fever = '" + fever + '\'' + 
			",name = '" + name + '\'' + 
			",genID = '" + genID + '\'' + 
			",dOA = '" + dOA + '\'' + 
			",street = '" + street + '\'' + 
			",cby = '" + cby + '\'' + 
			",breathingProblem = '" + breathingProblem + '\'' + 
			",age = '" + age + '\'' + 
			",longitude = '" + longitude + '\'' + 
			",dateQurantine = '" + dateQurantine + '\'' + 
			",poArrival = '" + poArrival + '\'' + 
			",hypertension = '" + hypertension + '\'' + 
			",talukCode = '" + talukCode + '\'' + 
			",hNo = '" + hNo + '\'' + 
			",diarrhea = '" + diarrhea + '\'' + 
			",coughSourThroat = '" + coughSourThroat + '\'' + 
			",city = '" + city + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",hIV = '" + hIV + '\'' + 
			",pOOrigin = '" + pOOrigin + '\'' + 
			",additional = '" + additional + '\'' + 
			",uRoleBy = '" + uRoleBy + '\'' + 
			",createdDate = '" + createdDate + '\'' + 
			",diabetes = '" + diabetes + '\'' + 
			",heartIssue = '" + heartIssue + '\'' + 
			"}";
		}
}