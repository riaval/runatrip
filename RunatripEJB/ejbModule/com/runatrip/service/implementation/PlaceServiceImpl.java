package com.runatrip.service.implementation;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.runatrip.dao.CategoryDAO;
import com.runatrip.dao.implementation.OrderBy;
import com.runatrip.ejb.Category;
import com.runatrip.ejb.Place;
import com.runatrip.ejb.Trip;
import com.runatrip.service.PlaceService;
import com.runatrip.sort.TripAdvisor;

@Stateless
public class PlaceServiceImpl implements PlaceService {

	@EJB
	CategoryDAO categoryDAO;
	
	@EJB
	TripAdvisor tripAdvisor;
	
	@Override
	public List<Category> getPlacesCategories() {
		return categoryDAO.findAll(OrderBy.ASC);
	}

	@Override
	public Trip getResult(List<Place> places) {
		Trip trip = new Trip();
		trip.setPlaces(places);
		try {
			return tripAdvisor.sort(trip);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
