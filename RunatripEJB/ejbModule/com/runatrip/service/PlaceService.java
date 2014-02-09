package com.runatrip.service;

import java.util.List;

import javax.ejb.Local;

import com.runatrip.ejb.Category;
import com.runatrip.ejb.Place;
import com.runatrip.ejb.Trip;

@Local
public interface PlaceService {

	public List<Category> getPlacesCategories();
	
	public Trip getResult(List<Place> places);
	
}
