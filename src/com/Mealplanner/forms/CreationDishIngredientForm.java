package com.Mealplanner.forms;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import com.Mealplanner.beans.Dish;
import com.Mealplanner.beans.DishIngredient;
import com.Mealplanner.beans.Ingredient;
import com.Mealplanner.dao.DAOException;
import com.Mealplanner.dao.DishIngredientDao;
import com.Mealplanner.dao.IngredientDao;

public final class CreationDishIngredientForm {
	private static final String FIELD_ID = "idIngredient";
	private static final String FIELD_COMMENT = "commentIngredient";
	private static final String FIELD_QUANTITY = "quantityIngredient";
	private static final String FIELD_UNIT = "unitIngredient";
	private static final String SESSION_DISH = "dish";

	@EJB
	private IngredientDao ingredientDao;

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();

	@EJB
	private DishIngredientDao dishIngredientDao;

	public CreationDishIngredientForm(DishIngredientDao dishIngredientDao) {
		this.dishIngredientDao = dishIngredientDao;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public String getResultat() {
		return resultat;
	}

	public DishIngredient createDishIngredient(HttpServletRequest request,
			String chemin) {
		String ingredientIdstr = getValeurChamp(request, FIELD_ID);
		String quantity = getValeurChamp(request, FIELD_QUANTITY);
		String unit = getValeurChamp(request, FIELD_UNIT);
		String comment = getValeurChamp(request, FIELD_COMMENT);
		Dish dish = (Dish) request.getSession().getAttribute(SESSION_DISH);

		DishIngredient dishIngredient = new DishIngredient();

		Long ingredientId = Long.valueOf(ingredientIdstr);
		// List<IngredientCategory> listcat = categoryDao.lister();
		// listcat.get(0);
		// IngredientCategory ingCategory = categoryDao.trouver(categoryid);

		Ingredient ingredient = new Ingredient();
		ingredient.setId(ingredientId);

		dishIngredient.setIngredient(ingredient);
		dishIngredient.setUnit(unit);
		dishIngredient.setComment(comment);
		dishIngredient.setDish(dish);
		checkQuantity(quantity, dishIngredient);

		/*
		 * traiterAdresse(adresse, client); traiterTelephone(telephone, client);
		 * traiterEmail(email, client); traiterImage(client, request, chemin);
		 */

		try {
			if (erreurs.isEmpty()) {
				dishIngredientDao.creer(dishIngredient);
				resultat = "Succès de la création de l'ingrédient.";
			} else {
				resultat = "Échec de la création de l'ingrédient.";
			}
		} catch (DAOException e) {
			setErreur("imprévu", "Erreur imprévue lors de la création.");
			resultat = "Échec de la création de l'ingrédient : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
			e.printStackTrace();
		}

		return dishIngredient;
	}

	private void checkQuantity(String quantity, DishIngredient dishIngredient) {
		Double quantityValue = (double) -1;
		try {
			quantityValue = validationQuantity(quantity);
		} catch (FormValidationException e) {
			setErreur(FIELD_QUANTITY, e.getMessage());
		}
		dishIngredient.setQuantity(quantityValue);
	}

	private Double validationQuantity(String quantity)
			throws FormValidationException {
		Double temp;
		if (quantity != null) {
			try {
				temp = Double.valueOf(quantity);
				if (temp < 0) {
					throw new FormValidationException(
							"Quantity must be positive.");
				}
			} catch (NumberFormatException e) {
				temp = (double) -1;
				throw new FormValidationException("Quantity must be a number");
			}
		} else {
			temp = (double) -1;
			throw new FormValidationException("Please enter a quantity");
		}
		return temp;
	}

	/*
	 * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	 */
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