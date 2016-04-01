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

import com.Mealplanner.beans.Ingredient;
import com.Mealplanner.beans.IngredientCategory;
import com.Mealplanner.dao.IngredientCategoryDao;
import com.Mealplanner.dao.IngredientDao;
import com.Mealplanner.forms.CreationIngredientForm;

@WebServlet(urlPatterns = { "/addIngredient" }, initParams = @WebInitParam(name = "chemin", value = "/fichiers/images/"))
@MultipartConfig(location = "/tmp", maxFileSize = 2 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024, fileSizeThreshold = 1024 * 1024)
public class CreationIngredient extends HttpServlet {
	public static final String CHEMIN = "chemin";
	public static final String ATT_INGREDIENT = "ingredient";
	public static final String ATT_FORM = "form";
	public static final String SESSION_INGREDIENTS = "ingredients";

	public static final String VUE_SUCCES = "/WEB-INF/showIngredients.jsp";
	public static final String VUE_FORM = "/WEB-INF/addIngredient.jsp";

	@EJB
	private IngredientDao ingredientDao;
	@EJB
	private IngredientCategoryDao CategoryDao;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<Long, String> Categories = new HashMap<Long, String>();
		List<IngredientCategory> listcategories = CategoryDao.lister();
		listcategories
				.sort(IngredientCategory.IngredientCategoryNameComparator);

		for (IngredientCategory category : listcategories) {
			Categories.put(category.getId(), category.getName());
		}
		request.setAttribute("Categories", Categories);

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
		CreationIngredientForm form = new CreationIngredientForm(ingredientDao);

		/*
		 * Traitement de la requête et récupération du bean en résultant
		 */
		Ingredient ingredient = form.creerIngredient(request, chemin);
		ingredient.setCategory(CategoryDao.trouver(ingredient.getCategory()
				.getId()));

		/* Ajout du bean et de l'objet métier à l'objet requête */
		request.setAttribute(ATT_INGREDIENT, ingredient);
		request.setAttribute(ATT_FORM, form);

		/* Si aucune erreur */
		if (form.getErreurs().isEmpty()) {
			/* Alors récupération de la map des ingrédients dans la session */
			HttpSession session = request.getSession();
			Map<Long, Ingredient> ingredients = (HashMap<Long, Ingredient>) session
					.getAttribute(SESSION_INGREDIENTS);
			/* Si aucune map n'existe, alors initialisation d'une nouvelle map */
			if (ingredients == null) {
				ingredients = new HashMap<Long, Ingredient>();
			}
			/* Puis ajout du client courant dans la map */
			ingredients.put(ingredient.getId(), ingredient);
			/* Et enfin (ré)enregistrement de la map en session */
			session.setAttribute(SESSION_INGREDIENTS, ingredients);

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