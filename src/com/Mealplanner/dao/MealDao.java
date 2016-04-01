package com.Mealplanner.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.Mealplanner.beans.Meal;

@Stateless
public class MealDao {

	// Injection du manager, qui s'occupe de la connexion avec la BDD
	@PersistenceContext(unitName = "bdd_mealplanner_PU")
	private EntityManager em;

	public Meal trouver(long id) throws DAOException {
		try {
			return em.find(Meal.class, id);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public void creer(Meal Meal) throws DAOException {
		try {
			em.persist(Meal);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public List<Meal> getMealPerDay(String YYYYMMDD) throws DAOException {
		try {
			TypedQuery<Meal> query = em.createQuery(
					"SELECT m FROM Meal m WHERE m.day = :date", Meal.class);
			query.setParameter("date", YYYYMMDD);
			return query.getResultList();

		} catch (Exception e) {
			// throw new DAOException(e);
			return null;
		}
	}

	public List<Meal> lister() throws DAOException {
		try {
			TypedQuery<Meal> query = em.createQuery("SELECT c FROM Meal c ",
					Meal.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public void supprimer(Meal Meal) throws DAOException {
		try {
			em.remove(em.merge(Meal));
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public List<String> listGroceries() throws DAOException {
		try {
			String querystr = "SELECT i.ING_NAME, SUM(di.INGDISH_QUANTITY) as Quantity,di.INGDISH_UNIT FROM Meal m";
			querystr += "JOIN MEAL_DISH md ";
			querystr += "ON m.MEAL_ID = md.MEAL_ID ";
			querystr += "JOIN Dish d ";
			querystr += "on md.DISH_ID = d.DISH_ID ";
			querystr += "JOIN DishIngredient di ";
			querystr += "ON d.DISH_ID = di.DISH_ID ";
			querystr += "JOIN Ingredient i ";
			querystr += "ON di.ING_ID = i.ING_ID ";
			querystr += "WHERE MEAL_DAY in ('20160330') ";
			querystr += "GROUP BY i.ING_NAME, di.INGDISH_UNIT ";
			querystr += "ORDER BY 1,2,3 ";
			Query query = em.createNativeQuery(querystr);
			return query.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
}