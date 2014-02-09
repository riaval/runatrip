package com.runatrip.dao.implementation;

import javax.ejb.Stateless;

import com.runatrip.dao.CategoryDAO;
import com.runatrip.ejb.Category;

@Stateless
public class CategoryDAOImpl extends DAOImpl<Category> implements CategoryDAO {

	public CategoryDAOImpl() {
		super(Category.class);
	}

}
