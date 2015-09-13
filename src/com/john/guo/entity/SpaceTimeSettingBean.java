package com.john.guo.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class SpaceTimeSettingBean implements Serializable {
	
	private String startTime;
	private String endTime;
	private ArrayList<String> selectedDays;
	
	public SpaceTimeSettingBean(String startTime, String endTime, ArrayList<String> selectedDays) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.selectedDays = selectedDays;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public ArrayList<String> getSelectedDays() {
		return selectedDays;
	}

	public void setSelectedDays(ArrayList<String> selectedDays) {
		this.selectedDays = selectedDays;
	}
	
}
