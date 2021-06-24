package com.bean;

public class Plan {
	
	private int planID;
	private String planName;
	private String typeOfPlan;
	private String tariff;
	private int validity;
	private double rental;

	public Plan(int planID, String planName, String typeOfPlan, String tariff, int validity, double rental) {
		
		this.planID = planID;
		this.planName = planName;
		this.typeOfPlan = typeOfPlan;
		this.tariff = tariff;
		this.validity = validity;
		this.rental = rental;
	}
	
	public Plan() {
		
	}

	public int getPlanID() {
		return planID;
	}
	public void setPlanID(int planID) {
		this.planID = planID;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getTypeOfPlan() {
		return typeOfPlan;
	}
	public void setTypeOfPlan(String typeOfPlan) {
		this.typeOfPlan = typeOfPlan;
	}
	public String getTariff() {
		return tariff;
	}
	public void setTariff(String tariff) {
		this.tariff = tariff;
	}
	public int getValidity() {
		return validity;
	}
	public void setValidity(int validity) {
		this.validity = validity;
	}
	public double getRental() {
		return rental;
	}
	public void setRental(double rental) {
		this.rental = rental;
	}
	
	
}