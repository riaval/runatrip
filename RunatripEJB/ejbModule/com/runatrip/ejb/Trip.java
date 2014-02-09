package com.runatrip.ejb;

import java.util.List;

public class Trip {

	private Long id;
	private String city;
	private List<Place> places;
	private String url;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public List<Place> getPlaces() {
		return places;
	}
	public void setPlaces(List<Place> places) {
		this.places = places;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
