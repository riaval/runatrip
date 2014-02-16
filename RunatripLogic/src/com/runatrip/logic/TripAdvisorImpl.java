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
		//TODO все время работаем с сылкой на объект. 
		//Если нужно работать с копией - нужно реализовать глубокое копирование
		//и ниже нужно скопировать trip
		
		List<Place> listOfPlases = trip.getPlaces();
		if(listOfPlases == null){
			return null;
		}
		
		//проверка: заполнины ли поля поля долгота, широта и адресс 
		//если нет - заполняются
		for(Place place : listOfPlases){
			checkCords(place);
		}
		if(listOfPlases.size() == 1){
			return trip;
		}
		for (int startPoint = 0; startPoint < listOfPlases.size()-1; startPoint++){
			sortPlasis(startPoint, listOfPlases);
//			System.out.println("______________________________________----______");
//			System.out.println(listOfPlases);
//			System.out.println("______________________________________----______");
		}	
	
		trip.setPlaces(listOfPlases);
		return trip;
	}


	private void sortPlasis(int startPoint, List<Place> listOfPlases) throws IOException, JSONException {
		if(listOfPlases.size()- startPoint == 1){
			return;
		}		
	    final String baseUrl = "http://maps.googleapis.com/maps/api/distancematrix/json";// путь к Geocoding API по HTTP
	    final Map<String, String> params = Maps.newHashMap();
	    params.put("sensor", "false");// указывает, исходит ли запрос на геокодирование от устройства с датчиком
	    params.put("language", "ru");// язык данных
	    params.put("mode", "driving");// идем пешком, может быть driving, walking, bicycling
	
	    // адрес или координаты отправных пунктов
	    final String[] origins = { listOfPlases.get(startPoint).getLatitude()+","+listOfPlases.get(startPoint).getLongitude(),};
	    params.put("origins", Joiner.on('|').join(origins));
	    
	    // адрес или координаты пунктов назначения
	   final String[] destionations= new String [listOfPlases.size()-1-startPoint];
	   	int index = 0;
	    for (int searchPoint = startPoint+1; searchPoint < listOfPlases.size(); searchPoint++){
	    	String coordinats =listOfPlases.get(searchPoint).getLatitude()+","+listOfPlases.get(searchPoint).getLongitude();
	    	destionations [index] = coordinats;
	    	index++;
	    	
	    }
	         
	    // в запросе адреса должны разделяться символом '|'
	    params.put("destinations", Joiner.on('|').join(destionations));
	    
	    final String url = baseUrl + '?' + encodeParams(params);// генерируем путь с параметрами
//	    System.out.println(url); // Можем проверить что вернет этот путь в браузере
	    
	    final JSONObject response = JsonReader.read(url);// делаем запрос к вебсервису и получаем от него ответ
	    final JSONObject location = response.getJSONArray("rows").getJSONObject(0);
	    final JSONArray arrays = location.getJSONArray("elements");// Здесь лежат все рассчитанные значения
	    // Ищем путь на который мы потратим минимум времени
	    final JSONObject result = Ordering.from(new Comparator<JSONObject>(){
	        @Override
	        public int compare(final JSONObject o1, final JSONObject o2) {
	            final Integer duration1 = getDurationValue(o1);
	            final Integer duration2 = getDurationValue(o2);
	            return duration1.compareTo(duration2);// Сравниваем по времени в пути
	        }

	        /**
	         * Возвращает время в пути
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
	    }).min(new AbstractIterator<JSONObject>() {// К сожалению JSONArray нельзя итереровать, по этому обернем его
	        private int index = 0;

	        @Override
	        protected JSONObject computeNext() {
	            try {
	                JSONObject result;
	                if (index < arrays.length()) {
	                    final String destionation = destionations[index];
	                    result = arrays.getJSONObject(index++);
	                    result.put("address", destionation);// Добавим сразу в структуру и адрес, потому как его нет в
	                    // этом расчёте
	                } else {
	                    result = endOfData();
	                }
	                return result;
	            } catch (final JSONException e) {
	                throw new RuntimeException(e);
	            }
	        }
	    });
	    final double distance = result.getJSONObject("distance").getDouble("value")/1000;// расстояние в километрах
	    final int duration = result.getJSONObject("duration").getInt("value")/60;// время в пути в минутах
	    final String cords = result.getString("address");// адрес
	    listOfPlases.get(startPoint).setDistanceToNext(distance);
	    listOfPlases.get(startPoint).setTimeToNext(duration);
	    int nextPlaceIndex = findIndex(startPoint, listOfPlases, cords);
	    
	    swith(startPoint+1, nextPlaceIndex, listOfPlases);
	    
	    
	    //Распечатка результата на консоль:
//	    String tempStrFrom = listOfPlases.get(startPoint).getAddress();
//	    String tempStrTo = listOfPlases.get(startPoint+1).getAddress();
//	    System.out.println("От "+ tempStrFrom + " до " +tempStrTo +"\nРасстояние: " + distance + "\nПродолжительность времени: " + duration);
	  
	
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
	
	
	//Проверяет на наличие координат в Plase
	//Если кординат нет - заполняет (получает координаты из google по адресу достопримечательности)
	private void checkCords(Place place) throws IOException, JSONException {
		if(place == null || place.getLatitude()!=0.0 && place.getLongitude()!=0.0 || place.getAddress().isEmpty()){
			return;			
		}
	    final String baseUrl = "http://maps.googleapis.com/maps/api/geocode/json";// путь к Geocoding API по HTTP
	    final Map<String, String> params = Maps.newHashMap();
	    params.put("sensor", "false");// исходит ли запрос на геокодирование от устройства с датчиком местоположения
	    params.put("address", place.getAddress());// адрес, который нужно геокодировать
	    final String url = baseUrl + '?' + encodeParams(params);// генерируем путь с параметрами
//	    System.out.println(url);// Путь, что бы можно было посмотреть в браузере ответ службы
	    final JSONObject response = JsonReader.read(url);// делаем запрос к вебсервису и получаем от него ответ
	    // как правило наиболее подходящий ответ первый и данные о координатах можно получить по пути
	    // //results[0]/geometry/location/lng и //results[0]/geometry/location/lat
	    JSONObject location = response.getJSONArray("results").getJSONObject(0);
	    location = location.getJSONObject("geometry");
	    location = location.getJSONObject("location");
	    final double lat = location.getDouble("lat");// широта
	    final double lng = location.getDouble("lng");// долгота
	    place.setLatitude(lat);
	    place.setLongitude(lng);
	}
	

	private String encodeParams(Map<String, String> params) {
		String paramsUrl = Joiner.on('&').join(// получаем значение вида
												// key1=value1&key2=value2...
				Iterables.transform(params.entrySet(),
						new Function<Entry<String, String>, String>() {
							@Override
							public String apply(Entry<String, String> input) {
								try {
									final StringBuffer buffer = new StringBuffer();
									buffer.append(input.getKey());// получаем
																	// значение
																	// вида
																	// key=value
									buffer.append('=');
									buffer.append(URLEncoder.encode(
											input.getValue(), "utf-8"));// кодируем
																		// строку
																		// в
																		// соответствии
																		// со
																		// стандартом
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
