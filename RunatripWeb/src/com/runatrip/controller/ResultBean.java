package com.runatrip.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.TreeNode;

import com.runatrip.ejb.Place;
import com.runatrip.ejb.Trip;
import com.runatrip.service.implementation.PlaceServiceImpl;

@ManagedBean
public class ResultBean {
	
	@EJB
	private PlaceServiceImpl placeServiceImpl;
	
	private TreeNode[] selectedNodes;
	private List<Place> places;
	
	public String confirm() {		
		List<Place> places = new ArrayList<Place>();
    	for (TreeNode treeNode : selectedNodes) {
    		if (treeNode.isLeaf()) {
    			Place place = (Place) treeNode.getData();
    			places.add(place);
			}
//    		System.out.println(place.getTitle());
		}
    	Trip trip = placeServiceImpl.getResult(places);
    	this.places = trip.getPlaces();
    	
    	return "result";
    }
	
    public TreeNode[] getSelectedNodes() {  
        return selectedNodes;  
    }  
  
    public void setSelectedNodes(TreeNode[] selectedNodes) {  
        this.selectedNodes = selectedNodes;  
    }

	public List<Place> getPlaces() {
		return places;
	}

	public void setPlaces(List<Place> places) {
		this.places = places;
	}
	
}
