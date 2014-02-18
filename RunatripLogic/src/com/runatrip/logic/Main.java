package com.runatrip.logic;

import java.io.IOException;

import java.util.*;
import org.json.JSONException;


public class Main {
	public static void main(final String[] args) throws IOException, JSONException {
		Trip testTrip = new Trip();
		testTrip = init();
		
		print(testTrip);		
		TripAdvisor advisor = new TripAdvisorImpl();
		testTrip = advisor.sort(testTrip);
		System.out.println("_____________________________________________________________");
		
		print(testTrip);
	}
	
	
	public static void print(Trip trip){
		if(trip == null){
			return;
		}
		for (int i = 0; i < trip.getPlaces().size(); i++) {
			System.out.println("Indeks jelementa: "+i+" jeto: "+trip.getPlaces().get(i));
			
		}
		
	}
	public static Trip init (){
		Trip testTrip = new Trip();
		Place place1 = new Place();		
		Place place2 = new Place();
		Place place3 = new Place();
		Place place4 = new Place();
		Place place5 = new Place();
		Place place6 = new Place();
		Place place7 = new Place();
		Place place8 = new Place();	
		place1.setAddress("Ukraine, Lugansk, Sovetskaya 21");
		place2.setAddress("Russia, Moscow, Sovetskaya 21");
		place3.setAddress("Ukraine, Kiev, Sovetskaya 21");
		place4.setAddress("Ukraine, Lviv, Sovetskaya 21");
		place5.setAddress("Ukraine, Odessa, Sovetskaya 21");
		place6.setAddress("Ukraine, Kharkiv, Sovetskaya 21");
		place7.setAddress("Ukraine, Donetsk, Sovetskaya 21");
		place8.setAddress("Ukraine, Dnepropetrovsk, Sovetskaya 21");
		place1.setId(1L);
		place2.setId(2L);
		place3.setId(3L);
		place4.setId(4L);
		place5.setId(5L);
		place6.setId(6L);
		place7.setId(7L);
		place8.setId(8L);		
		List<Place> listOfPlaces = new ArrayList <Place>(); 
		listOfPlaces.add(0, place1);
		listOfPlaces.add(1,place2);
		listOfPlaces.add(2,place3);
		listOfPlaces.add(3,place4);
		listOfPlaces.add(4,place5);
		listOfPlaces.add(5,place6);
		listOfPlaces.add(6,place7);
		listOfPlaces.add(7,place8);
		
		testTrip.setPlaces(listOfPlaces);	
		return testTrip;
	}
}


