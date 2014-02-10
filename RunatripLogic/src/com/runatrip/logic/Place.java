package com.runatrip.logic;

public class Place {
	protected Long id;
	private String title;
	private String address;
	private double latitude;
	private double longitude;
	private int timeToNext;//minutes
	private double distanceToNext; //distance
	
	
	public int getTimeToNext() {
		return timeToNext;
	}
	public void setTimeToNext(int timeToNext) {
		this.timeToNext = timeToNext;
	}
	public double getDistanceToNext() {
		return distanceToNext;
	}
	public void setDistanceToNext(double distanceToNext) {
		this.distanceToNext = distanceToNext;
	}
	public Place() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Place [id=" + id + ", title=" + title + ", address=" + address
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", timeToNext=" + timeToNext + ", distanceToNext="
				+ distanceToNext + "]\n";
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	

}
