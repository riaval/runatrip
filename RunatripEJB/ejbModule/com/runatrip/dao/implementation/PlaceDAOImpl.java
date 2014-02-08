package com.runatrip.dao.implementation;

import javax.ejb.Stateless;

import com.runatrip.dao.PlaceDAO;
import com.runatrip.ejb.Place;

@Stateless
public class PlaceDAOImpl extends DAOImpl<Place> implements PlaceDAO {

	public PlaceDAOImpl() {
		super(Place.class);
	}

}
