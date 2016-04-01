package com.Mealplanner.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.Mealplanner.beans.Dish;
import com.Mealplanner.beans.DishIngredient;

@Stateless
public class DishIngredientDao {

	// Injection du manager, qui s'occupe de la connexion avec la BDD
	@PersistenceContext(unitName = "bdd_mealplanner_PU")
	private EntityManager em;

	public DishIngredient trouver(long id) throws DAOException {
		try {
			return em.find(DishIngredient.class, id);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public List<DishIngredient> findForDishId(Dish dish) throws DAOException {
		try {
			TypedQuery<DishIngredient> query = em.createQuery(
					"SELECT d FROM DishIngredient d WHERE d.dish = :dish",
					DishIngredient.class);
			query.setParameter("dish", dish);
			return query.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public void creer(DishIngredient DishIngredient) throws DAOException {
		try {
			em.persist(DishIngredient);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public List<DishIngredient> lister() throws DAOException {
		try {
			TypedQuery<DishIngredient> query = em.createQuery(
					"SELECT d FROM DishIngredient d", DishIngredient.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public void supprimer(DishIngredient dishIngredient) throws DAOException {
		try {
			em.remove(em.merge(dishIngredient));
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
}