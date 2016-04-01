package com.Mealplanner.servlet;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Mealplanner.beans.Dish;
import com.Mealplanner.beans.DishIngredient;
import com.Mealplanner.dao.DishDao;
import com.Mealplanner.dao.DishIngredientDao;

@WebServlet(urlPatterns = { "/editDish" })
public class ShowDishIngredients extends HttpServlet {
	public static final String SESSION_DISHINGREDIENTS = "dishingredients";
	public static final String SESSION_DISH = "dish";
	public static final String ATT_FORM = "form";

	public static final String VUE = "/WEB-INF/showDishIngredients.jsp";

	@EJB
	private DishDao dishDao;
	@EJB
	private DishIngredientDao dishIngredientDao;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long idDish = Long.valueOf(request.getParameter("id"));

		Dish dish = dishDao.trouver(idDish);

		List<DishIngredient> dishIngredients = dishIngredientDao
				.findForDishId(dish);
		dishIngredients.sort(DishIngredient.IngredientNameComparator);

		HttpSession session = request.getSession();
		session.setAttribute(SESSION_DISH, dish);

		session.setAttribute(SESSION_DISHINGREDIENTS, dishIngredients);

		/* À la réception d'une requête GET, affichage de la liste des clients */
		this.getServletContext().getRequestDispatcher(VUE)
				.forward(request, response);
	}
}