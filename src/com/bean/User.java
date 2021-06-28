package com.bean;

public class User {

	private int userID;
	private String userPWD;
	private String userGROUP;
	private String name;
	private String email;
	private String address;
	private String contact_no;
	private int planID;
	private String planName;
	
	public User (int userID, String userPWD, String userGROUP, String name, String email, String address, String contact_no, int planID) {
		
		this.userID	= userID;
		this.userPWD	= userPWD;
		this.userGROUP	= userGROUP;
		this.name	= name;
		this.email	= email;
		this.address	= address;
		this.contact_no	= contact_no;
		this.planID	= planID;
		
	}
	
	public User (String name, String email, String address, String contact_no, String planName) {
		
		this.name		= name;
		this.email		= email;
		this.address	= address;
		this.contact_no	= contact_no;
		this.planName	= planName;
		
	}
	
	public User (int userID, String name, String email, String contact_no, String planName) {
		
		this.userID = userID;
		this.name	= name;
		this.email	= email;
		this.contact_no	= contact_no;
		this.planName	= planName;
		
	}
	
	public User() {
		
	}
	
	public int getUserID() {
		return userID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public String getUserPWD() {
		return userPWD;
	}
	
	public void setUserPWD(String userPWD) {
		this.userPWD	= userPWD;
	}
	
	public String getUserGROUP() {
		return userGROUP;
	}
	
	public void setUserGROUP(String userGROUP) {
		this.userGROUP	= userGROUP;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name	= name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email	= email;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address	= address;
	}
	
	public String getContact_No() {
		return contact_no;
	}
	
	public void setContact_No(String contact_no) {
		this.contact_no	= contact_no;
	}
	
	public int getPlanID() {
		return planID;
	}
	
	public void setPlanID(int planID) {
		this.planID	= planID;
	}
	
	public String getPlanName() {
		return planName;
	}
	
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	
}
