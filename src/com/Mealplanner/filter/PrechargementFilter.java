package com.Mealplanner.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;

import com.Mealplanner.beans.Day;
import com.Mealplanner.beans.Dish;
import com.Mealplanner.beans.Ingredient;
import com.Mealplanner.beans.IngredientCategory;
import com.Mealplanner.beans.Meal;
import com.Mealplanner.beans.Week;
import com.Mealplanner.dao.DishDao;
import com.Mealplanner.dao.DishIngredientDao;
import com.Mealplanner.dao.IngredientCategoryDao;
import com.Mealplanner.dao.IngredientDao;
import com.Mealplanner.dao.MealDao;

@WebFilter(urlPatterns = { "/*" })
public class PrechargementFilter implements Filter {
	public static final String ATT_SESSION_INGREDIENTS = "ingredients";
	public static final String ATT_SESSION_DISHES = "dishes";
	public static final String ATT_SESSION_WEEK = "week";
	public static final String ATT_SESSION_CATEGORIES = "categories";

	public static final String ATT_SESSION_MEALS = "meals";

	@EJB
	private MealDao mealDao;

	@EJB
	private IngredientDao ingredientDao;

	@EJB
	private DishIngredientDao dishIngredientDao;

	@EJB
	private IngredientCategoryDao categoryDao;

	@EJB
	private DishDao dishDao;

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		/* Cast de l'objet request */
		HttpServletRequest request = (HttpServletRequest) req;

		/* Récupération de la session depuis la requête */
		HttpSession session = request.getSession();

		/*
		 * Si la map des clients n'existe pas en session, alors l'utilisateur se
		 * connecte pour la première fois et nous devons précharger en session
		 * les infos contenues dans la BDD.
		 */
		if (session.getAttribute(ATT_SESSION_INGREDIENTS) == null) {
			/*
			 * Récupération de la liste des clients existants, et enregistrement
			 * en session
			 */
			List<Ingredient> listeIngredient = ingredientDao.lister();
			listeIngredient.sort(Ingredient.IngredientNameComparator);
			Map<Long, Ingredient> mapIngredients = new HashMap<Long, Ingredient>();
			for (Ingredient ingredient : listeIngredient) {
				mapIngredients.put(ingredient.getId(), ingredient);
			}
			session.setAttribute(ATT_SESSION_INGREDIENTS, mapIngredients);
		}

		/*
		 * if (session.getAttribute(ATT_SESSION_DISHINGREDIENTS) == null) {
		 */
		/*
		 * Récupération de la liste des clients existants, et enregistrement en
		 * session
		 */
		/*
		 * List<DishIngredient> listeDishIngredient = dishIngredientDao
		 * .lister(); Map<Long, DishIngredient> mapDishIngredients = new
		 * HashMap<Long, DishIngredient>(); for (DishIngredient dishIngredient :
		 * listeDishIngredient) { mapDishIngredients.put(dishIngredient.getId(),
		 * dishIngredient); } session.setAttribute(ATT_SESSION_DISHINGREDIENTS,
		 * mapDishIngredients); }
		 */

		if (session.getAttribute(ATT_SESSION_DISHES) == null) {
			/*
			 * Récupération de la liste des clients existants, et enregistrement
			 * en session
			 */
			List<Dish> listDish = dishDao.lister();
			Map<Long, Dish> mapDishes = new HashMap<Long, Dish>();
			for (Dish dish : listDish) {
				mapDishes.put(dish.getId(), dish);
			}
			session.setAttribute(ATT_SESSION_DISHES, mapDishes);
		}

		if (session.getAttribute(ATT_SESSION_MEALS) == null) {
			/*
			 * Récupération de la liste des clients existants, et enregistrement
			 * en session
			 */
			List<Meal> listMeal = mealDao.lister();
			Map<Long, Meal> mapMeals = new HashMap<Long, Meal>();
			for (Meal meal : listMeal) {
				mapMeals.put(meal.getId(), meal);
			}
			session.setAttribute(ATT_SESSION_MEALS, mapMeals);
		}

		/*
		 * De même pour la map des commandes
		 */
		if (session.getAttribute(ATT_SESSION_CATEGORIES) == null) {
			/*
			 * Récupération de la liste des commandes existantes, et
			 * enregistrement en session
			 */

			List<IngredientCategory> listeCategories = categoryDao.lister();
			Map<Long, IngredientCategory> mapCategories = new HashMap<Long, IngredientCategory>();
			for (IngredientCategory category : listeCategories) {
				mapCategories.put(category.getId(), category);
			}
			session.setAttribute(ATT_SESSION_CATEGORIES, mapCategories);
		}

		if (session.getAttribute(ATT_SESSION_WEEK) == null) {
			/*
			 * Récupération de la liste des commandes existantes, et
			 * enregistrement en session
			 */

			Week week = new Week();
			DateTime dt = DateTime.now();
			String yearWeek = dt.toString("yyyyww");
			TreeMap<String, Day> weeks = (TreeMap<String, Day>) week
					.getNext7Days(yearWeek);
			for (Map.Entry<String, Day> day : weeks.entrySet()) {

				day.getValue().setMeal(mealDao);
			}
			weeks.keySet();
			session.setAttribute(ATT_SESSION_WEEK, weeks);
		}

		/* Pour terminer, poursuite de la requête en cours */
		chain.doFilter(request, res);
	}

	public void destroy() {
	}
}