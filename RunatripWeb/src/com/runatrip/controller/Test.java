package com.runatrip.controller;

import java.util.ArrayList;
import java.util.List;

import com.runatrip.ejb.Place;
import com.runatrip.ejb.Category;

public class Test {
	
	public static List<Category> getTrips() {
		Place place11 = new Place("Title1.1", "Address1.1");
		Place place12 = new Place("Title1.2", "Address1.2");
		Place place13 = new Place("Title1.3", "Address1.3");
		Place place14 = new Place("Title1.4", "Address1.4");
		
		Place place21 = new Place("Title2.1", "Address2.1");
		Place place22 = new Place("Title2.2", "Address2.2");
		Place place23 = new Place("Title2.3", "Address2.3");
		
		Place place31 = new Place("Title3.1", "Address3.1");
		Place place32 = new Place("Title3.2", "Address3.2");
		Place place33 = new Place("Title3.3", "Address3.3");
		
		List<Place> places1 = new ArrayList<>();
		places1.add(place11);
		places1.add(place12);
		places1.add(place13);
		places1.add(place14);
		Category trip1 = new Category("trip1", places1);
		
		List<Place> places2 = new ArrayList<>();
		places2.add(place21);
		places2.add(place22);
		places2.add(place23);
		Category trip2 = new Category("trip2", places2);
		
		List<Place> places3 = new ArrayList<>();
		places3.add(place31);
		places3.add(place32);
		places3.add(place33);
		Category trip3 = new Category("trip3", places3);
		
		List<Category> trips = new ArrayList<>();
		trips.add(trip1);
		trips.add(trip2);
		trips.add(trip3);
		
		return trips;
	}
	
}
