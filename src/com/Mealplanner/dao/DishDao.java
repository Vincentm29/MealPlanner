package com.Mealplanner.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.Mealplanner.beans.Dish;

@Stateless
public class DishDao {

	// Injection du manager, qui s'occupe de la connexion avec la BDD
	@PersistenceContext(unitName = "bdd_mealplanner_PU")
	private EntityManager em;

	public Dish trouver(long id) throws DAOException {
		try {
			return em.find(Dish.class, id);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public void creer(Dish dish) throws DAOException {
		try {
			em.persist(dish);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public List<Dish> lister() throws DAOException {
		try {
			TypedQuery<Dish> query = em.createQuery("SELECT d FROM Dish d",
					Dish.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public void supprimer(Dish dish) throws DAOException {
		try {
			em.remove(em.merge(dish));
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
}