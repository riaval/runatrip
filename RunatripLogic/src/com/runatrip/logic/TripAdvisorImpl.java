package com.runatrip.logic;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.sun.xml.internal.bind.v2.runtime.Coordinator;

public class TripAdvisorImpl implements TripAdvisor{
	
	public TripAdvisorImpl() {
		super();
	}

	
	@Override
	public Trip sort(Trip trip) throws JSONException, IOException {
		List<Place> listOfPlases = trip.getPlaces();
		if(listOfPlases == null){
			return null;
		}
		//proverka: zapolniny li polja polja dolgota, shirota i adress 
		//esli net - zapolnjajutsja
		for(Place place : listOfPlases){
			checkCords(place);
		}
		if(listOfPlases.size() == 1){
			return trip;
		}
		for (int startPoint = 0; startPoint < listOfPlases.size()-1; startPoint++){
			sortPlasis(startPoint, listOfPlases);
		}	
	
		trip.setPlaces(listOfPlases);
		return trip;
	}


	private void sortPlasis(int startPoint, List<Place> listOfPlases) throws IOException, JSONException {
		if(listOfPlases.size()- startPoint == 1){
			return;
		}		
	    final String baseUrl = "http://maps.googleapis.com/maps/api/distancematrix/json";//  put' k Geocoding API po HTTP
	    final Map<String, String> params = Maps.newHashMap();
	    params.put("sensor", "false");// ukazyvaet, ishodit li zapros na geokodirovanie ot ustrojstva s datchikom
	    params.put("language", "ru");// jazyk dannyh
	    params.put("mode", "driving");// idem peshkom, mozhet byt' driving, walking, bicycling
	    // adres ili koordinaty otpravnyh punktov
	    final String[] origins = { listOfPlases.get(startPoint).getLatitude()+","+listOfPlases.get(startPoint).getLongitude(),};
	    params.put("origins", Joiner.on('|').join(origins));
	    
	    // adres ili koordinaty punktov naznachenija
	   final String[] destionations= new String [listOfPlases.size()-1-startPoint];
	   	int index = 0;
	    for (int searchPoint = startPoint+1; searchPoint < listOfPlases.size(); searchPoint++){
	    	String coordinats =listOfPlases.get(searchPoint).getLatitude()+","+listOfPlases.get(searchPoint).getLongitude();
	    	destionations [index] = coordinats;
	    	index++;
	    	
	    }
	         
	    // v zaprose adresa dolzhny razdeljat'sja simvolom '|'
	    params.put("destinations", Joiner.on('|').join(destionations));
	    
	    final String url = baseUrl + '?' + encodeParams(params);// generiruem put' s parametrami
//	    System.out.println(url); // Mozhem proverit' chto vernet jetot put' v brauzere
	    
	    final JSONObject response = JsonReader.read(url);// delaem zapros k vebservisu i poluchaem ot nego otvet
	    final JSONObject location = response.getJSONArray("rows").getJSONObject(0);
	    final JSONArray arrays = location.getJSONArray("elements");// Zdes' lezhat vse rasschitannye znachenija
	    // Ishhem put' na kotoryj my potratim minimum vremeni
	    final JSONObject result = Ordering.from(new Comparator<JSONObject>(){
	        @Override
	        public int compare(final JSONObject o1, final JSONObject o2) {
	            final Integer duration1 = getDurationValue(o1);
	            final Integer duration2 = getDurationValue(o2);
	            return duration1.compareTo(duration2);// Sravnivaem po vremeni v puti
	        }

	        /**
	         * Vozvrashhaet vremja v puti
	         * 
	         * @param obj
	         * @return
	         */
	        private int getDurationValue(final JSONObject obj) {
	            try {
	                return obj.getJSONObject("duration").getInt("value");
	            } catch (final JSONException e) {
	                throw new RuntimeException(e);
	            }
	        }
	    }).min(new AbstractIterator<JSONObject>() {// K sozhaleniju JSONArray nel'zja itererovat', po jetomu obernem ego
	        private int index = 0;

	        @Override
	        protected JSONObject computeNext() {
	            try {
	                JSONObject result;
	                if (index < arrays.length()) {
	                    final String destionation = destionations[index];
	                    result = arrays.getJSONObject(index++);
	                    result.put("address", destionation);//  Dobavim srazu v strukturu i adres, potomu kak ego net v
	                    // jetom raschjote
	                } else {
	                    result = endOfData();
	                }
	                return result;
	            } catch (final JSONException e) {
	                throw new RuntimeException(e);
	            }
	        }
	    });
	    final double distance = result.getJSONObject("distance").getDouble("value")/1000;// rasstojanie v kilometrah
	    final int duration = result.getJSONObject("duration").getInt("value")/60;// vremja v puti v minutah
	    final String cords = result.getString("address");// adres
	    listOfPlases.get(startPoint).setDistanceToNext(distance);
	    listOfPlases.get(startPoint).setTimeToNext(duration);
	    int nextPlaceIndex = findIndex(startPoint, listOfPlases, cords);
	    
	    swith(startPoint+1, nextPlaceIndex, listOfPlases);
	    
	    
	   //Raspechatka rezul'tata na konsol':
		//String tempStrFrom = listOfPlases.get(startPoint).getAddress();
		//String tempStrTo = listOfPlases.get(startPoint+1).getAddress();
		//System.out.println("Ot "+ tempStrFrom + " do " +tempStrTo +"\nRasstojanie: " + distance + "\nProdolzhitel'nost' vremeni: " + duration);
	  
	
	}
	    
	private int findIndex (int startOfSearch, List<Place> listOfPlases, String targetCoordinats){
		int rezult;
		for (rezult=startOfSearch; rezult < listOfPlases.size()-1; rezult++) {
			String coordinats = listOfPlases.get(rezult).getLatitude()+","+listOfPlases.get(rezult).getLongitude();
			if(coordinats.equals(targetCoordinats)){
				break;
			}
		} 
		return rezult;
	}
	

	private void swith (int indexFrom, int indexTo, List<Place> listOfPlases){
//		if(listOfPlases == null || indexFrom==indexTo || indexFrom < 0 || indexFrom > listOfPlases.size()-1 ||  indexTo < 0 || indexTo > listOfPlases.size()-1){
//			return;
//		}	
		Place tempPlace = listOfPlases.get(indexFrom);
		listOfPlases.set(indexFrom, listOfPlases.get(indexTo));
		listOfPlases.set(indexTo, tempPlace);		
		
	}
	

	//Proverjaet na nalichie koordinat v Plase
	//Esli kordinat net - zapolnjaet (poluchaet koordinaty iz google po adresu dostoprimechatel'nosti)
	private void checkCords(Place place) throws IOException, JSONException {
		if(place == null || place.getLatitude()!=0.0 && place.getLongitude()!=0.0 || place.getAddress().isEmpty()){
			return;			
		}
	    final String baseUrl = "http://maps.googleapis.com/maps/api/geocode/json";// put' k Geocoding API po HTTP
	    final Map<String, String> params = Maps.newHashMap();
	    params.put("sensor", "false");// ishodit li zapros na geokodirovanie ot ustrojstva s datchikom mestopolozhenija
	    params.put("address", place.getAddress());// adres, kotoryj nuzhno geokodirovat'
	    final String url = baseUrl + '?' + encodeParams(params);// generiruem put' s parametrami
//	    System.out.println(url);// Put', chto by mozhno bylo posmotret' v brauzere otvet sluzhby
	    final JSONObject response = JsonReader.read(url);// delaem zapros k vebservisu i poluchaem ot nego otvet
	    // kak pravilo naibolee podhodjashhij otvet pervyj i dannye o koordinatah mozhno poluchit' po puti
	    // //results[0]/geometry/location/lng i //results[0]/geometry/location/lat
	    JSONObject location = response.getJSONArray("results").getJSONObject(0);
	    location = location.getJSONObject("geometry");
	    location = location.getJSONObject("location");
	    final double lat = location.getDouble("lat");// shirota
	    final double lng = location.getDouble("lng");// dolgota
	    place.setLatitude(lat);
	    place.setLongitude(lng);
	}
	

	private String encodeParams(Map<String, String> params) {
		String paramsUrl = Joiner.on('&').join(// poluchaem znachenie vida
												// key1=value1&key2=value2...
				Iterables.transform(params.entrySet(),
						new Function<Entry<String, String>, String>() {
							@Override
							public String apply(Entry<String, String> input) {
								try {
									final StringBuffer buffer = new StringBuffer();
									buffer.append(input.getKey());// poluchaem
																	// znachenie
																	// vida
																	// key=value
									buffer.append('=');
									buffer.append(URLEncoder.encode(
											input.getValue(), "utf-8"));// kodiruem
																		// stroku
																		// v
																		// sootvetstvii
																		// so
																		// standartom
																		// HTML
																		// 4.01
									return buffer.toString();
								} catch (UnsupportedEncodingException e) {
									throw new RuntimeException(e);
								}
							}
						}));
		return paramsUrl;
	}

}
