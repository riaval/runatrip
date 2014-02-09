package com.runatrip.dao.implementation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.runatrip.dao.DAO;


public abstract class DAOImpl<T> implements DAO<T> {

	private Class<T> persistClass;
	private String findAllQuery;
	private String countQuery;

//	@PersistenceContext(type = PersistenceContextType.EXTENDED, unitName = "runatripPU")
	protected EntityManager em;

	public DAOImpl(Class<T> clazz) {
		persistClass = clazz;

		StringBuilder output = new StringBuilder();
		output.append("SELECT e FROM ").append(persistClass.getSimpleName())
				.append(" AS e").append(" ORDER BY e.id ");
		findAllQuery = output.toString();

		output = new StringBuilder();
		output.append("SELECT COUNT(e) FROM ")
				.append(persistClass.getSimpleName()).append(" AS e");
		countQuery = output.toString();
	}

	@Override
	public T save(T entity) {
		em.persist(entity);

		return entity;
	}

	@Override
	public void merge(T entity) {
		em.merge(entity);
	}

	@Override
	public void delete(T entity) {
		em.remove(entity);
	}

	@Override
	public List<T> findAll(OrderBy orderBy) {
		TypedQuery<T> query = em.createQuery(findAllQuery + orderBy,
				persistClass);

		return findMany(query);
	}

	@Override
	public List<T> loadPart(int firstIndex, int amount, OrderBy orderBy) {
		TypedQuery<T> query = em.createQuery(findAllQuery + orderBy,
				persistClass);

		query.setFirstResult(firstIndex);
		query.setMaxResults(amount);

		return findMany(query);
	}

	@Override
	public T findByID(int id) {
		T entity = em.find(persistClass, id);

		return entity;
	}

	@Override
	public int count() {
		Query query = em.createQuery(countQuery);

		return ((Long) query.getSingleResult()).intValue();
	}

	protected T findOne(TypedQuery<T> query) {
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	protected List<T> findMany(TypedQuery<T> query) {
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<T> findPart(TypedQuery<T> query, int firstIndex, int amount) {
		query.setFirstResult(firstIndex);
		query.setMaxResults(amount);

		return findMany(query);
	}

}
