package com.Mealplanner.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Mealplanner.beans.Dish;
import com.Mealplanner.beans.DishIngredient;
import com.Mealplanner.beans.Ingredient;
import com.Mealplanner.dao.DishDao;
import com.Mealplanner.dao.DishIngredientDao;
import com.Mealplanner.dao.IngredientDao;
import com.Mealplanner.forms.CreationDishIngredientForm;

@WebServlet(urlPatterns = { "/addDishIngredient" }, initParams = @WebInitParam(name = "chemin", value = "/fichiers/images/"))
@MultipartConfig(location = "/tmp", maxFileSize = 2 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024, fileSizeThreshold = 1024 * 1024)
public class CreateDishIngredient extends HttpServlet {
	public static final String CHEMIN = "chemin";
	public static final String SESSION_DISH = "dish";
	public static final String ATT_INGREDIENT = "ingredient";
	public static final String ATT_DISHINGREDIENT = "dishingredient";
	public static final String ATT_FORM = "form";
	public static final String SESSION_DISHINGREDIENTS = "dishingredients";

	public static final String VUE_SUCCES = "/WEB-INF/showDishIngredients.jsp";
	public static final String VUE_FORM = "/WEB-INF/addDishIngredient.jsp";

	@EJB
	private IngredientDao ingredientDao;
	@EJB
	private DishIngredientDao dishIngredientDao;
	@EJB
	private DishDao dishDao;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Long idDish = Long.valueOf(request.getParameter("id"));

		Dish dish = dishDao.trouver(idDish);

		Map<Long, String> ingredients = new HashMap<Long, String>();
		List<Ingredient> listIngredients = ingredientDao.lister();
		listIngredients.sort(Ingredient.IngredientNameComparator);

		for (Ingredient ingredient : listIngredients) {
			ingredients.put(ingredient.getId(), ingredient.getName());
		}
		request.setAttribute(ATT_INGREDIENT, ingredients);

		HttpSession session = request.getSession();
		session.setAttribute(SESSION_DISH, dish);

		/*
		 * À la réception d'une requête GET, simple affichage du formulaire
		 */
		this.getServletContext().getRequestDispatcher(VUE_FORM)
				.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * Lecture du paramètre 'chemin' passé à la servlet via la déclaration
		 * dans le web.xml
		 */
		String chemin = this.getServletConfig().getInitParameter(CHEMIN);

		/*
		 * Préparation de l'objet formulaire
		 */
		CreationDishIngredientForm form = new CreationDishIngredientForm(
				dishIngredientDao);
		/*
		 * Traitement de la requête et récupération du bean en résultant
		 */
		DishIngredient dishIngredient = form.createDishIngredient(request,
				chemin);
		dishIngredient.setIngredient(ingredientDao.trouver(dishIngredient
				.getIngredient().getId()));

		/* Ajout du bean et de l'objet métier à l'objet requête */
		request.setAttribute(ATT_DISHINGREDIENT, dishIngredient);
		request.setAttribute(ATT_FORM, form);

		/* Si aucune erreur */
		if (form.getErreurs().isEmpty()) {
			/* Alors récupération de la map des ingrédients dans la session */
			HttpSession session = request.getSession();
			Dish dish2 = (Dish) session.getAttribute("dish");

			List<DishIngredient> dishIngredients = (List<DishIngredient>) session
					.getAttribute(SESSION_DISHINGREDIENTS);

			// Map<Long, DishIngredient> dishIngredients = (Map<Long,
			// DishIngredient>) session
			// .getAttribute(SESSION_DISHINGREDIENTS);
			/* Si aucune map n'existe, alors initialisation d'une nouvelle map */
			/*
			 * if (dishIngredients == null) { dishIngredients = new
			 * HashMap<Long, DishIngredient>(); }
			 */
			/* Puis ajout du client courant dans la map */
			dishIngredients.add(dishIngredient);
			// dishIngredients.put(dishIngredient.getId(), dishIngredient);
			/* Et enfin (ré)enregistrement de la map en session */
			session.setAttribute(SESSION_DISHINGREDIENTS, dishIngredients);

			/* Affichage de la fiche récapitulative */
			this.getServletContext().getRequestDispatcher(VUE_SUCCES)
					.forward(request, response);
		} else {
			/* Sinon, ré-affichage du formulaire de création avec les erreurs */
			this.getServletContext().getRequestDispatcher(VUE_FORM)
					.forward(request, response);
		}
	}
}