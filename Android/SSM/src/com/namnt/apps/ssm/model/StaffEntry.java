package com.namnt.apps.ssm.model;

import java.io.Serializable;

public class StaffEntry implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String imei;
	private String staffId;
	private String staffLocale;
	private String staffAvatar;
	private String serial;
	public StaffEntry() {
		
	}
	public StaffEntry(String name, String imei, String staffId, String staffLocale,
			String staffAvatar) {
		super();
		this.name = name;
		this.imei = imei;
		this.staffId = staffId;
		this.staffLocale = staffLocale;
		this.staffAvatar = staffAvatar;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getStaffLocale() {
		return staffLocale;
	}
	public void setStaffLocale(String staffLocale) {
		this.staffLocale = staffLocale;
	}
	public String getStaffAvatar() {
		return staffAvatar;
	}
	public void setStaffAvatar(String staffAvatar) {
		this.staffAvatar = staffAvatar;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	
	
}
