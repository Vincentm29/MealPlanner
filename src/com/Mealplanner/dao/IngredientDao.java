package com.Mealplanner.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.Mealplanner.beans.Ingredient;

@Stateless
public class IngredientDao {

	// Injection du manager, qui s'occupe de la connexion avec la BDD
	@PersistenceContext(unitName = "bdd_mealplanner_PU")
	private EntityManager em;

	public Ingredient trouver(long id) throws DAOException {
		try {
			return em.find(Ingredient.class, id);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public void creer(Ingredient Ingredient) throws DAOException {
		try {
			em.persist(Ingredient);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public List<Ingredient> lister() throws DAOException {
		try {
			TypedQuery<Ingredient> query = em.createQuery(
					"SELECT i FROM Ingredient i ORDER BY i.name",
					Ingredient.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public void supprimer(Ingredient Ingredient) throws DAOException {
		try {
			em.remove(em.merge(Ingredient));
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
}