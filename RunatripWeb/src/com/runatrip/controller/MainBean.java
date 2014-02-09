package com.runatrip.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

import com.runatrip.dao.implementation.OrderBy;
import com.runatrip.ejb.Category;
import com.runatrip.ejb.Place;
import com.runatrip.service.implementation.PlaceServiceImpl;

@ManagedBean
public class MainBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private PlaceServiceImpl placeServiceImpl;
	
	private TreeNode root;  
    
    private String info;
      
    @PostConstruct
    public void init() {
//    	List<Category> categories = Test.getTrips();
    	List<Category> categories = placeServiceImpl.getPlacesCategories();
    	
        root = new CheckboxTreeNode(new Place("root", "root"), null);
        
        for (Category trip : categories) {
        	TreeNode first = new CheckboxTreeNode(new Place(trip.getName(), "-"), root);
        	for (Place place : trip.getPlaces()) {
        		TreeNode second = new CheckboxTreeNode(place, first);
			}
		}
    }  
      
    public void onNodeSelect(NodeSelectEvent event) {
    	TreeNode node = event.getTreeNode();
    	Place place = (Place) node.getData();
    	
    	if (!node.getParent().equals(root)) {
        	info = place.getInfo();
		} else {
			info = place.getTitle();
		}
    }
    
    public TreeNode getRoot() {  
        return root;  
    }  

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	} 
	
}
