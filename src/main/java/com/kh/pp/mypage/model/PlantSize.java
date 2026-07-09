package com.kh.pp.mypage.model;

public enum PlantSize {
	
	smallPlant(0.5),
	middlePlant(1),
	bigPlant(1.5);
	
	private final double coefficient;
	
	
	PlantSize(double coefficient){
		this.coefficient = coefficient;
	}
	
	public double getCoefficient() {
		return coefficient;
	}

}
