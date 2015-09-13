package com.john.guo.entity;

import java.io.Serializable;

public class ServiceRecordBean implements Serializable {
	private String time;
	private String tips;
	private boolean isOver;

	public ServiceRecordBean(String time, String tips, boolean isOver) {
		super();
		this.time = time;
		this.tips = tips;
		this.isOver = isOver;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public boolean isOver() {
		return isOver;
	}

	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}

}
