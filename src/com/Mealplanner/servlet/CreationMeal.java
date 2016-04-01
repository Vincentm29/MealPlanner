package com.Mealplanner.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Mealplanner.beans.Day;
import com.Mealplanner.beans.Dish;
import com.Mealplanner.beans.Meal;
import com.Mealplanner.dao.DishDao;
import com.Mealplanner.dao.MealDao;
import com.Mealplanner.forms.CreateMealForm;

@WebServlet(urlPatterns = { "/addMeal" }, initParams = @WebInitParam(name = "chemin", value = "/fichiers/images/"))
@MultipartConfig(location = "/tmp", maxFileSize = 2 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024, fileSizeThreshold = 1024 * 1024)
public class CreationMeal extends HttpServlet {
	public static final String CHEMIN = "chemin";
	public static final String ATT_MESSAGES = "messages";
	public static final String SESSION_WEEK = "week";
	public static final String SESSION_DAY = "day";
	public static final String ATT_DATE = "date";
	public static final String ATT_DISHES = "dishes";
	public static final String ATT_MEAL = "meal";
	public static final String ATT_FORM = "form";

	public static final String VUE_SUCCES = "/WEB-INF/showCalendar.jsp";
	public static final String VUE_FORM = "/WEB-INF/addMeal.jsp";

	@EJB
	private MealDao mealDao;

	@EJB
	private DishDao dishDao;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* Initialisation de l'objet Java et récupération des messages */

		HttpSession session = request.getSession();
		TreeMap<String, Day> week = (TreeMap<String, Day>) session
				.getAttribute(SESSION_WEEK);

		String dateId = request.getParameter(ATT_DATE);
		Day day = week.get(dateId);

		session.setAttribute(SESSION_DAY, day);

		Map<Long, String> dishes = new HashMap<Long, String>();
		List<Dish> listdishes = dishDao.lister();
		listdishes.sort(Dish.DishNameComparator);

		for (Dish dish : listdishes) {
			dishes.put(dish.getId(), dish.getName());
		}
		request.setAttribute(ATT_DISHES, dishes);

		/* Transmission vers la page en charge de l'affichage des résultats */
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
		CreateMealForm form = new CreateMealForm(mealDao);

		/*
		 * Traitement de la requête et récupération du bean en résultant
		 */
		Meal meal = form.createMeal(request, chemin);

		/* Ajout du bean et de l'objet métier à l'objet requête */
		request.setAttribute(ATT_MEAL, meal);
		request.setAttribute(ATT_FORM, form);

		/* Si aucune erreur */
		if (form.getErreurs().isEmpty()) {
			/* Alors récupération de la map des ingrédients dans la session */
			HttpSession session = request.getSession();

			TreeMap<String, Day> week = (TreeMap<String, Day>) session
					.getAttribute(SESSION_WEEK);

			Day day = (Day) session.getAttribute(SESSION_DAY);
			day.setMeal(mealDao);

			this.getServletContext().getRequestDispatcher(VUE_SUCCES)
					.forward(request, response);
		} else {
			/* Sinon, ré-affichage du formulaire de création avec les erreurs */
			this.getServletContext().getRequestDispatcher(VUE_FORM)
					.forward(request, response);
		}
	}
}