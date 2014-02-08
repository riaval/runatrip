package com.runatrip.dao.implementation;

import javax.ejb.Stateless;

import com.runatrip.dao.TripDAO;
import com.runatrip.ejb.Trip;


@Stateless
public class TripDAOImpl extends DAOImpl<Trip> implements TripDAO {

	public TripDAOImpl() {
		super(Trip.class);
	}

}
