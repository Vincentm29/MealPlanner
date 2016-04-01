package com.Mealplanner.forms;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import com.Mealplanner.beans.Day;
import com.Mealplanner.beans.Dish;
import com.Mealplanner.beans.Meal;
import com.Mealplanner.dao.DAOException;
import com.Mealplanner.dao.DishDao;
import com.Mealplanner.dao.MealDao;

public final class CreateMealForm {
	private static final String FIELD_DISHID = "idDish";
	private static final String FIELD_TYPE = "typeMeal";
	private static final String SESSION_DAY = "day";

	@EJB
	private DishDao dishDao;

	@EJB
	private MealDao mealDao;

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();

	public CreateMealForm(MealDao mealDao) {
		this.mealDao = mealDao;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public String getResultat() {
		return resultat;
	}

	public Meal createMeal(HttpServletRequest request, String chemin) {
		String dishId = getValeurChamp(request, FIELD_DISHID);
		String typeMeal = getValeurChamp(request, FIELD_TYPE);
		Day day = (Day) request.getSession().getAttribute(SESSION_DAY);

		Meal meal = new Meal();
		Dish dish = new Dish();
		dish.setId(Long.valueOf(dishId));

		meal.setDate(day.getDate(), typeMeal);
		meal.addDish(dish);
		meal.setMealType(typeMeal);
		/*
		 * traiterAdresse(adresse, client); traiterTelephone(telephone, client);
		 * traiterEmail(email, client); traiterImage(client, request, chemin);
		 */

		try {
			if (erreurs.isEmpty()) {
				mealDao.creer(meal);
				resultat = "Succès de la création de l'ingrédient.";
			} else {
				resultat = "Échec de la création de l'ingrédient.";
			}
		} catch (DAOException e) {
			setErreur("imprévu", "Erreur imprévue lors de la création.");
			resultat = "Échec de la création de l'ingrédient : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
			e.printStackTrace();
		}

		return meal;
	}

	private void setErreur(String champ, String message) {
		erreurs.put(champ, message);
	}

	/*
	 * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
	 * sinon.
	 */
	private static String getValeurChamp(HttpServletRequest request,
			String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur;
		}
	}

}