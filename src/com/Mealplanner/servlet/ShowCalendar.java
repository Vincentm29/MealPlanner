package com.Mealplanner.servlet;

import java.io.IOException;
import java.util.List;
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
import com.Mealplanner.dao.MealDao;

/**
 * Servlet implementation class ShowCalendar
 */
@WebServlet(urlPatterns = { "/showCalendar" }, initParams = @WebInitParam(name = "chemin", value = "/fichiers/images/"))
@MultipartConfig(location = "/tmp", maxFileSize = 2 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024, fileSizeThreshold = 1024 * 1024)
public class ShowCalendar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE = "/WEB-INF/showCalendar.jsp";
	public static final String SESSION_WEEK = "week";
	public static final String VUE_SUCCES = "/WEB-INF/showList.jsp";
	public static final String ATT_DATES = "dates";

	@EJB
	private MealDao mealDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowCalendar() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		TreeMap<String, Day> week = (TreeMap<String, Day>) session
				.getAttribute(SESSION_WEEK);

		this.getServletContext().getRequestDispatcher(VUE)
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String[] listdates = request.getParameterValues(ATT_DATES);
		for (int i = 0; i < listdates.length; ++i)
			System.out.println("  " + listdates[i]);

		List<String> listgroceries = mealDao.listGroceries();

		this.getServletContext().getRequestDispatcher(VUE)
				.forward(request, response);
	}
}
