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

import com.Mealplanner.beans.Dish;
import com.Mealplanner.dao.DishDao;
import com.Mealplanner.forms.CreateDishForm;

@WebServlet(urlPatterns = { "/addDish" }, initParams = @WebInitParam(name = "chemin", value = "/fichiers/images/"))
@MultipartConfig(location = "/tmp", maxFileSize = 2 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024, fileSizeThreshold = 1024 * 1024)
public class CreateDish extends HttpServlet {
	public static final String CHEMIN = "chemin";
	public static final String ATT_DISH = "dish";
	public static final String ATT_FORM = "form";
	public static final String SESSION_DISHES = "dishes";

	public static final String VUE_SUCCES = "/WEB-INF/showDishes.jsp";
	public static final String VUE_FORM = "/WEB-INF/addDish.jsp";

	@EJB
	private DishDao dishDao;

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
		CreateDishForm form = new CreateDishForm(dishDao);

		/*
		 * Traitement de la requête et récupération du bean en résultant
		 */
		Dish dish = form.createDish(request, chemin);

		/* Ajout du bean et de l'objet métier à l'objet requête */
		request.setAttribute(ATT_DISH, dish);
		request.setAttribute(ATT_FORM, form);

		/* Si aucune erreur */
		if (form.getErreurs().isEmpty()) {
			/* Alors récupération de la map des ingrédients dans la session */
			HttpSession session = request.getSession();
			Map<Long, Dish> dishes = (Map<Long, Dish>) session
					.getAttribute(SESSION_DISHES);
			/* Si aucune map n'existe, alors initialisation d'une nouvelle map */
			if (dishes == null) {
				dishes = new HashMap<Long, Dish>();
			}
			/* Puis ajout du client courant dans la map */
			dishes.put(dish.getId(), dish);
			/* Et enfin (ré)enregistrement de la map en session */
			session.setAttribute(SESSION_DISHES, dishes);

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