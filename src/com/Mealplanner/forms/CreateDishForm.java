package com.Mealplanner.forms;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.text.WordUtils;

import com.Mealplanner.beans.Dish;
import com.Mealplanner.dao.DAOException;
import com.Mealplanner.dao.DishDao;

public final class CreateDishForm {
	private static final String FIELD_NAME = "nameDish";
	private static final String FIELD_CATEGORY = "categoryDish";
	private static final String FIELD_NBPEOPLE = "nbpeopleDish";
	private static final String FIELD_COOKINGTIME = "cookingtimeDish";
	private static final String FIELD_PREPTIME = "preptimeDish";
	private static final String FIELD_PRICERANGE = "pricerangeDish";

	@EJB
	private DishDao dishDao;

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();

	public CreateDishForm(DishDao dishDao) {
		this.dishDao = dishDao;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public String getResultat() {
		return resultat;
	}

	public Dish createDish(HttpServletRequest request, String chemin) {
		String name = getValeurChamp(request, FIELD_NAME);
		String category = getValeurChamp(request, FIELD_CATEGORY);
		String nbpeople = getValeurChamp(request, FIELD_NBPEOPLE);
		String cookingtime = getValeurChamp(request, FIELD_COOKINGTIME);
		String preptime = getValeurChamp(request, FIELD_PREPTIME);
		String pricerange = getValeurChamp(request, FIELD_PRICERANGE);

		Dish dish = new Dish();
		checkName(name, dish);
		checkNbPeople(nbpeople, dish);
		checkCookingTime(cookingtime, dish);
		checkPreparationTime(preptime, dish);
		dish.setPriceRange(pricerange);
		dish.setCategory(category);

		/*
		 * traiterAdresse(adresse, client); traiterTelephone(telephone, client);
		 * traiterEmail(email, client); traiterImage(client, request, chemin);
		 */

		try {
			if (erreurs.isEmpty()) {
				dishDao.creer(dish);
				resultat = "Succès de la création de l'ingrédient.";
			} else {
				resultat = "Échec de la création de l'ingrédient.";
			}
		} catch (DAOException e) {
			setErreur("imprévu", "Erreur imprévue lors de la création.");
			resultat = "Échec de la création de l'ingrédient : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
			e.printStackTrace();
		}

		return dish;
	}

	private void checkNbPeople(String nbpeople, Dish dish) {
		long nbpeopleValue = -1;
		try {
			nbpeopleValue = validationNbPeople(nbpeople);
		} catch (FormValidationException e) {
			setErreur(FIELD_NBPEOPLE, e.getMessage());
		}
		dish.setNumberOfPeople(nbpeopleValue);
	}

	private long validationNbPeople(String nbpeople)
			throws FormValidationException {
		long temp;
		if (nbpeople != null) {
			try {
				temp = Long.valueOf(nbpeople);
				if (temp < 0) {
					throw new FormValidationException(
							"Number of people must be positive.");
				}
			} catch (NumberFormatException e) {
				temp = -1;
				throw new FormValidationException(
						"the number of people  must be a number");
			}
		} else {
			temp = -1;
			throw new FormValidationException(
					"Please enter the number of people");
		}
		return temp;
	}

	private void checkName(String name, Dish dish) {
		try {
			WordUtils.capitalizeFully(name);
			validationName(name);
		} catch (FormValidationException e) {
			setErreur(FIELD_NAME, e.getMessage());
		}
		dish.setName(name);
	}

	private void validationName(String name) throws FormValidationException {
		if (name != null) {
			if (name.length() < 2) {
				throw new FormValidationException(
						"The name must be at least 2 character long");
			}
		} else {
			throw new FormValidationException("Please enter a name");
		}
	}

	private void checkCookingTime(String cookingtime, Dish dish) {
		long cookingtimeValue = -1;
		try {
			cookingtimeValue = validationCookingTime(cookingtime);
		} catch (FormValidationException e) {
			setErreur(FIELD_NBPEOPLE, e.getMessage());
		}
		dish.setCookingTime(cookingtimeValue);
	}

	private long validationCookingTime(String cookingtime)
			throws FormValidationException {
		long temp;
		if (cookingtime != null) {
			try {
				temp = Long.valueOf(cookingtime);
				if (temp < 0) {
					throw new FormValidationException(
							"Cooking time must be positive.");
				}
			} catch (NumberFormatException e) {
				temp = -1;
				throw new FormValidationException(
						"Cooking time must be a number");
			}
		} else {
			temp = 0;
		}
		return temp;
	}

	private void checkPreparationTime(String preptime, Dish dish) {
		long preptimeValue = -1;
		try {
			preptimeValue = validationPreparationTime(preptime);
		} catch (FormValidationException e) {
			setErreur(FIELD_NBPEOPLE, e.getMessage());
		}
		dish.setPreparationTime(preptimeValue);
	}

	private long validationPreparationTime(String preptime)
			throws FormValidationException {
		long temp;
		if (preptime != null) {
			try {
				temp = Long.valueOf(preptime);
				if (temp < 0) {
					throw new FormValidationException(
							"Preparation time must be positive.");
				}
			} catch (NumberFormatException e) {
				temp = -1;
				throw new FormValidationException(
						"Preparation time must be a number");
			}
		} else {
			temp = -1;
			throw new FormValidationException("Please enter preparation time");
		}
		return temp;
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