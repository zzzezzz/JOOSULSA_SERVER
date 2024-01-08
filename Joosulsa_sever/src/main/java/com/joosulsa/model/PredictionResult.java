package com.joosulsa.model;

public class PredictionResult {
	private String className;
	
	public PredictionResult() {}
	
	public PredictionResult(String className) {
		this.className = className;
	}
	
	public String getclassName() {
		return className;
	}
	
	public void setclassName(String className) {
		this.className = className;
	}
}
