package com.runatrip.sort;

import java.io.IOException;

import javax.ejb.Local;

import com.runatrip.ejb.Trip;

@Local
public interface TripAdvisor {
	
	// TODO throw JSONException, IOException
	public Trip sort(Trip trip) throws Exception;//JSONException, IOException;
	
}
