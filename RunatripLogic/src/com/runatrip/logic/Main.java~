package com.runatrip.logic;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;


public class Main {
	public static void main(final String[] args) throws IOException, JSONException {
		Trip testTrip = new Trip();
		testTrip = init();
		for(Place place:testTrip.getPlaces()){
			System.out.println(place);
		}

		
		
		TripAdvisor advisor = new TripAdvisorImpl();
		testTrip = advisor.sort(testTrip);
		System.out.println("_____________________________________________________________");
		
		for(Place place:testTrip.getPlaces()){
			System.out.println(place);
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
		
		
		place1.setAddress("Украина, Луганск, Совецкая 21");
		place2.setAddress("Россия, Москва, Совецкая 21");
		place3.setAddress("Украина, Киев, Совецкая 21");
		place4.setAddress("Украина, Львов, Совецкая 21");
		place5.setAddress("Украина, Одесса, Советская 21");
		place6.setAddress("Украина, Харьков, Совецкая 21");
		place7.setAddress("Украина, Донецк, Советская 21");
		place8.setAddress("Украина, Днепропетровск, Совецкая 21");


		place1.setId(1L);
		place2.setId(2L);
		place3.setId(3L);
		place4.setId(4L);
		place5.setId(5L);
		place6.setId(6L);
		place7.setId(7L);
		place8.setId(8L);
		
	
		
//		place1.setLongitude(37.516838);
//		place2.setLatitude(55.7358925);
//		place2.setLongitude(37.5254195);
//		place3.setLatitude(55.736617);
//		place3.setLongitude(37.5254495);
//		place4.setLatitude(55.736717);
//		place4.setLongitude(37.514838);
//		place5.setLatitude(55.736317);
//		place5.setLongitude(37.516538);
		
		List<Place> listOfPlaces = new ArrayList <Place>(); 
		listOfPlaces.add(0, place2);
		listOfPlaces.add(1,place1);
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





















//package com.runatrip.logic;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.Comparator;
//import java.util.Map;
//import java.util.Map.Entry;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import com.google.common.base.Function;
//import com.google.common.base.Joiner;
//import com.google.common.collect.AbstractIterator;
//import com.google.common.collect.Iterables;
//import com.google.common.collect.Maps;
//import com.google.common.collect.Ordering;
//
//
//public class Main {
//	public static void main(final String[] args) throws IOException, JSONException {
//	    final String baseUrl = "http://maps.googleapis.com/maps/api/distancematrix/json";// путь к Geocoding API по HTTP
//	    final Map<String, String> params = Maps.newHashMap();
//	    params.put("sensor", "false");// указывает, исходит ли запрос на геокодирование от устройства с датчиком
//	    params.put("language", "ru");// язык данных
//	    params.put("mode", "walking");// идем пешком, может быть driving, walking, bicycling
//	    // адрес или координаты отправных пунктов
//	    final String[] origins = { "55.7358925,37.5274195" };
//	    params.put("origins", Joiner.on('|').join(origins));
//	    // адрес или координаты пунктов назначения
//	    final String[] destionations = { //
////	            "Россия, Москва, станция метро Парк Победы", //
////	            "Россия, Москва, станция метро Кутузовская", //
//	            "Украина, Киев, Драгоманова 20",
//	            "55.736217,37.516838",
//	            //"55.7358925,37.5274195"
//	    };
//	    // в запросе адреса должны разделяться символом '|'
//	    params.put("destinations", Joiner.on('|').join(destionations));
//	    final String url = baseUrl + '?' + encodeParams(params);// генерируем путь с параметрами
//	    System.out.println(url); // Можем проверить что вернет этот путь в браузере
//	    final JSONObject response = JsonReader.read(url);// делаем запрос к вебсервису и получаем от него ответ
//	    final JSONObject location = response.getJSONArray("rows").getJSONObject(0);
//	    final JSONArray arrays = location.getJSONArray("elements");// Здесь лежат все рассчитанные значения
//	    // Ищем путь на который мы потратим минимум времени
//	    final JSONObject result = Ordering.from(new Comparator<JSONObject>(){
//	        @Override
//	        public int compare(final JSONObject o1, final JSONObject o2) {
//	            final Integer duration1 = getDurationValue(o1);
//	            final Integer duration2 = getDurationValue(o2);
//	            return duration1.compareTo(duration2);// Сравниваем по времени в пути
//	        }
//
//	        /**
//	         * Возвращает время в пути
//	         * 
//	         * @param obj
//	         * @return
//	         */
//	        private int getDurationValue(final JSONObject obj) {
//	            try {
//	                return obj.getJSONObject("duration").getInt("value");
//	            } catch (final JSONException e) {
//	                throw new RuntimeException(e);
//	            }
//	        }
//	    }).min(new AbstractIterator<JSONObject>() {// К сожалению JSONArray нельзя итереровать, по этому обернем его
//	        private int index = 0;
//
//	        @Override
//	        protected JSONObject computeNext() {
//	            try {
//	                JSONObject result;
//	                if (index < arrays.length()) {
//	                    final String destionation = destionations[index];
//	                    result = arrays.getJSONObject(index++);
//	                    result.put("address", destionation);// Добавим сразу в структуру и адрес, потому как его нет в
//	                    // этом расчёте
//	                } else {
//	                    result = endOfData();
//	                }
//	                return result;
//	            } catch (final JSONException e) {
//	                throw new RuntimeException(e);
//	            }
//	        }
//	    });
//	    final String distance = result.getJSONObject("distance").getString("text");// расстояние в километрах
//	    final String duration = result.getJSONObject("duration").getString("text");// время в пути
//	    final String address = result.getString("address");// адрес
//	    System.out.println(address + "\n" + distance + "\n" + duration);
//	}
//	
//	
//	 private static String encodeParams(Map<String, String> params) {
//	        String paramsUrl = Joiner.on('&').join(// получаем значение вида key1=value1&key2=value2...
//	                Iterables.transform(params.entrySet(), new Function<Entry<String, String>, String>() {
//	            @Override
//	            public String apply(Entry<String, String> input) {
//	                try {
//	                    final StringBuffer buffer = new StringBuffer();
//	                    buffer.append(input.getKey());// получаем значение вида key=value
//	                    buffer.append('=');
//	                    buffer.append(URLEncoder.encode(input.getValue(), "utf-8"));// кодируем строку в соответствии со стандартом HTML 4.01
//	                    return buffer.toString();
//	                } catch (UnsupportedEncodingException e) {
//	                    throw new RuntimeException(e);
//	                }
//	            }
//	        }));
//	        return paramsUrl;
//	    }
//}


