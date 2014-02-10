package com.runatrip.logic;

import java.io.IOException;

import org.json.JSONException;

//@Local
public interface TripAdvisor {
	public Trip sort(Trip trip) throws JSONException, IOException;
}
