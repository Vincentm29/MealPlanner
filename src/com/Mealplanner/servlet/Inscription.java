package com.Mealplanner.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Mealplanner.beans.Utilisateur;
import com.Mealplanner.dao.UtilisateurDao;
import com.Mealplanner.forms.InscriptionForm;

@WebServlet(urlPatterns = { "/inscription" })
public class Inscription extends HttpServlet {
	public static final String ATT_USER = "utilisateur";
	public static final String ATT_FORM = "form";
	public static final String VUE = "/WEB-INF/inscription.jsp";

	// Injection de notre EJB (Session Bean Stateless)
	@EJB
	private UtilisateurDao utilisateurDao;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* Affichage de la page d'inscription */
		this.getServletContext().getRequestDispatcher(VUE)
				.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* Préparation de l'objet formulaire */
		InscriptionForm form = new InscriptionForm(utilisateurDao);

		/* Traitement de la requête et récupération du bean en résultant */
		Utilisateur utilisateur = form.inscrireUtilisateur(request);

		/* Stockage du formulaire et du bean dans l'objet request */
		request.setAttribute(ATT_FORM, form);
		request.setAttribute(ATT_USER, utilisateur);

		this.getServletContext().getRequestDispatcher(VUE)
				.forward(request, response);
	}
}