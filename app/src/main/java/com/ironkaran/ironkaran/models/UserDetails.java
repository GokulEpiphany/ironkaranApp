package com.ironkaran.ironkaran.models;


public class UserDetails {

	private Long userId;
	private String address;
	private String pushToken;
	private String name;
	private int apartmentId;

	public int getApartmentId() {
		return apartmentId;
	}

	public void setApartmentId(int apartmentId) {
		this.apartmentId = apartmentId;
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public String getPushToken() {
		return pushToken;
	}
	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public UserDetails(Long userId, String address, String pushToken, String name, int apartmentId) {
		this.userId = userId;
		this.address = address;
		this.pushToken = pushToken;
		this.name = name;
		this.apartmentId = apartmentId;
	}

	@Override
	public String toString() {
		return "UserDetails{" +
				"userId=" + userId +
				", address='" + address + '\'' +
				", pushToken='" + pushToken + '\'' +
				", name='" + name + '\'' +
				", apartmentId=" + apartmentId +
				'}';
	}

	public UserDetails() {
	}
}
