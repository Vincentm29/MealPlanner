package com.Mealplanner.servlet;

import java.io.IOException;
import java.util.HashMap;
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

import com.Mealplanner.beans.IngredientCategory;
import com.Mealplanner.dao.IngredientCategoryDao;
import com.Mealplanner.forms.CreationIngredientCategoryForm;

@WebServlet(urlPatterns = { "/addIngredientCategory" }, initParams = @WebInitParam(name = "chemin", value = "/fichiers/images/"))
@MultipartConfig(location = "/tmp", maxFileSize = 2 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024, fileSizeThreshold = 1024 * 1024)
public class CreationIngredientCategory extends HttpServlet {
	public static final String CHEMIN = "chemin";
	public static final String ATT_CATEGORY = "category";
	public static final String ATT_FORM = "form";
	public static final String SESSION_CATEGORIES = "categories";

	public static final String VUE_SUCCES = "/WEB-INF/showIngredientCategories.jsp";
	public static final String VUE_FORM = "/WEB-INF/addIngredientCategory.jsp";

	@EJB
	private IngredientCategoryDao categoryDao;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		CreationIngredientCategoryForm form = new CreationIngredientCategoryForm(
				categoryDao);

		/*
		 * Traitement de la requête et récupération du bean en résultant
		 */
		IngredientCategory category = form.creerIngredientCategory(request,
				chemin);

		/* Ajout du bean et de l'objet métier à l'objet requête */
		request.setAttribute(ATT_CATEGORY, category);
		request.setAttribute(ATT_FORM, form);

		/* Si aucune erreur */
		if (form.getErreurs().isEmpty()) {
			/* Alors récupération de la map des ingrédients dans la session */
			HttpSession session = request.getSession();
			Map<Long, IngredientCategory> categories = (HashMap<Long, IngredientCategory>) session
					.getAttribute(SESSION_CATEGORIES);
			/* Si aucune map n'existe, alors initialisation d'une nouvelle map */
			if (categories == null) {
				categories = new HashMap<Long, IngredientCategory>();
			}
			/* Puis ajout du client courant dans la map */
			categories.put(category.getId(), category);
			/* Et enfin (ré)enregistrement de la map en session */
			session.setAttribute(SESSION_CATEGORIES, categories);

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