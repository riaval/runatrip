package com.runatrip.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

import com.runatrip.ejb.Place;

@ManagedBean
public class ResultBean {
	
	private TreeNode[] selectedNodes;
	private List<Place> plases;
	
	public String confirm() {		
		plases = new ArrayList<Place>();
    	for (TreeNode treeNode : selectedNodes) {
    		if (treeNode.isLeaf()) {
    			Place place = (Place) treeNode.getData();
        		plases.add(place);
			}
//    		System.out.println(place.getTitle());
		}
    	
    	return "result";
    }
	
    public TreeNode[] getSelectedNodes() {  
        return selectedNodes;  
    }  
  
    public void setSelectedNodes(TreeNode[] selectedNodes) {  
        this.selectedNodes = selectedNodes;  
    }

	public List<Place> getPlases() {
		System.out.println(plases);
		return plases;
	}

	public void setPlases(List<Place> plases) {
		this.plases = plases;
	}
	
}
