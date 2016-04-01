package com.Mealplanner.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.Mealplanner.beans.IngredientCategory;

@Stateless
public class IngredientCategoryDao {
	// Injection du manager, qui s'occupe de la connexion avec la BDD
	@PersistenceContext(unitName = "bdd_mealplanner_PU")
	private EntityManager em;

	public IngredientCategory trouver(long id) throws DAOException {
		try {
			return em.find(IngredientCategory.class, id);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public void creer(IngredientCategory IngredientCategory)
			throws DAOException {
		try {
			em.persist(IngredientCategory);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public List<IngredientCategory> lister() throws DAOException {
		try {
			TypedQuery<IngredientCategory> query = em.createQuery(
					"SELECT c FROM IngredientCategory c ORDER BY c.name",
					IngredientCategory.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public void supprimer(IngredientCategory IngredientCategory)
			throws DAOException {
		try {
			em.remove(em.merge(IngredientCategory));
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
}