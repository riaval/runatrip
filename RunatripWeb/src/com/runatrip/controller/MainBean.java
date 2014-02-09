package com.runatrip.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

import com.runatrip.ejb.Place;
import com.runatrip.ejb.Category;

@ManagedBean
public class MainBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private TreeNode root;  
    
    private String info;
      
    public MainBean() {
    	List<Category> trips = Test.getTrips();
    	
        root = new CheckboxTreeNode(new Place("root", "root"), null);
        
        for (Category trip : trips) {
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
