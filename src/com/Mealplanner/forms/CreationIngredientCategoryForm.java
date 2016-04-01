package com.Mealplanner.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.text.WordUtils;

import com.Mealplanner.beans.IngredientCategory;
import com.Mealplanner.dao.DAOException;
import com.Mealplanner.dao.IngredientCategoryDao;

public final class CreationIngredientCategoryForm {
	private static final String FIELD_NAME = "nameCategory";

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();
	private IngredientCategoryDao ingredientCategoryDao;

	public CreationIngredientCategoryForm(
			IngredientCategoryDao ingredientCategoryDao) {
		this.ingredientCategoryDao = ingredientCategoryDao;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public String getResultat() {
		return resultat;
	}

	public IngredientCategory creerIngredientCategory(
			HttpServletRequest request, String chemin) {
		String name = getValeurChamp(request, FIELD_NAME);

		IngredientCategory category = new IngredientCategory();

		/*
		 * IngredientCategoryDao ingCategoryDao = new IngredientCategoryDao();
		 * ingCategoryDao.trouver(Long.valueOf(category));
		 */
		checkName(name, category);

		/*
		 * traiterNom(nom, client); traiterPrenom(prenom, client);
		 * traiterAdresse(adresse, client); traiterTelephone(telephone, client);
		 * traiterEmail(email, client); traiterImage(client, request, chemin);
		 */

		try {
			if (erreurs.isEmpty()) {
				ingredientCategoryDao.creer(category);
				resultat = "Succès de la création de l'ingrédient.";
			} else {
				resultat = "Échec de la création de l'ingrédient.";
			}
		} catch (DAOException e) {
			setErreur("imprévu", "Erreur imprévue lors de la création.");
			resultat = "Échec de la création de l'ingrédient : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
			e.printStackTrace();
		}

		return category;
	}

	private void checkName(String name, IngredientCategory category) {
		try {
			WordUtils.capitalizeFully(name);
			validationName(name);
		} catch (FormValidationException e) {
			setErreur(FIELD_NAME, e.getMessage());
		}
		category.setName(name);
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

	/*
	 * private void traiterNom(String nom, Client client) { try {
	 * validationNom(nom); } catch (FormValidationException e) {
	 * setErreur(CHAMP_NOM, e.getMessage()); } client.setNom(nom); }
	 * 
	 * private void traiterPrenom(String prenom, Client client) { try {
	 * validationPrenom(prenom); } catch (FormValidationException e) {
	 * setErreur(CHAMP_PRENOM, e.getMessage()); } client.setPrenom(prenom); }
	 * 
	 * private void traiterAdresse(String adresse, Client client) { try {
	 * validationAdresse(adresse); } catch (FormValidationException e) {
	 * setErreur(CHAMP_ADRESSE, e.getMessage()); } client.setAdresse(adresse); }
	 * 
	 * private void traiterTelephone(String telephone, Client client) { try {
	 * validationTelephone(telephone); } catch (FormValidationException e) {
	 * setErreur(CHAMP_TELEPHONE, e.getMessage()); }
	 * client.setTelephone(telephone); }
	 * 
	 * private void traiterEmail(String email, Client client) { try {
	 * validationEmail(email); } catch (FormValidationException e) {
	 * setErreur(CHAMP_EMAIL, e.getMessage()); } client.setEmail(email); }
	 * 
	 * private void traiterImage(Client client, HttpServletRequest request,
	 * String chemin) { String image = null; try { image =
	 * validationImage(request, chemin); } catch (FormValidationException e) {
	 * setErreur(CHAMP_IMAGE, e.getMessage()); } client.setImage(image); }
	 * 
	 * private void validationNom(String nom) throws FormValidationException {
	 * if (nom != null) { if (nom.length() < 2) { throw new
	 * FormValidationException(
	 * "Le nom d'utilisateur doit contenir au moins 2 caractères."); } } else {
	 * throw new FormValidationException(
	 * "Merci d'entrer un nom d'utilisateur."); } }
	 * 
	 * private void validationPrenom(String prenom) throws
	 * FormValidationException { if (prenom != null && prenom.length() < 2) {
	 * throw new FormValidationException(
	 * "Le prénom d'utilisateur doit contenir au moins 2 caractères."); } }
	 * 
	 * private void validationAdresse(String adresse) throws
	 * FormValidationException { if (adresse != null) { if (adresse.length() <
	 * 10) { throw new FormValidationException(
	 * "L'adresse de livraison doit contenir au moins 10 caractères."); } } else
	 * { throw new FormValidationException(
	 * "Merci d'entrer une adresse de livraison."); } }
	 * 
	 * private void validationTelephone(String telephone) throws
	 * FormValidationException { if (telephone != null) { if
	 * (!telephone.matches("^\\d+$")) { throw new FormValidationException(
	 * "Le numéro de téléphone doit uniquement contenir des chiffres."); } else
	 * if (telephone.length() < 4) { throw new FormValidationException(
	 * "Le numéro de téléphone doit contenir au moins 4 chiffres."); } } else {
	 * throw new FormValidationException(
	 * "Merci d'entrer un numéro de téléphone."); } }
	 * 
	 * private void validationEmail(String email) throws FormValidationException
	 * { if (email != null &&
	 * !email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) { throw new
	 * FormValidationException( "Merci de saisir une adresse mail valide."); } }
	 * 
	 * private String validationImage(HttpServletRequest request, String chemin)
	 * throws FormValidationException {
	 * 
	 * Récupération du contenu du champ image du formulaire. Il faut ici
	 * utiliser la méthode getPart().
	 * 
	 * String nomFichier = null; InputStream contenuFichier = null; try { Part
	 * part = request.getPart(CHAMP_IMAGE); nomFichier = getNomFichier(part);
	 * 
	 * 
	 * Si la méthode getNomFichier() a renvoyé quelque chose, il s'agit donc
	 * d'un champ de type fichier (input type="file").
	 * 
	 * if (nomFichier != null && !nomFichier.isEmpty()) {
	 * 
	 * Antibug pour Internet Explorer, qui transmet pour une raison mystique le
	 * chemin du fichier local à la machine du client...
	 * 
	 * Ex : C:/dossier/sous-dossier/fichier.ext
	 * 
	 * On doit donc faire en sorte de ne sélectionner que le nom et l'extension
	 * du fichier, et de se débarrasser du superflu.
	 * 
	 * nomFichier = nomFichier.substring( nomFichier.lastIndexOf('/') +
	 * 1).substring( nomFichier.lastIndexOf('\\') + 1);
	 * 
	 * Récupération du contenu du fichier contenuFichier =
	 * part.getInputStream();
	 * 
	 * Extraction du type MIME du fichier depuis l'InputStream
	 * MimeUtil.registerMimeDetector
	 * ("eu.medsea.mimeutil.detector.MagicMimeMimeDetector"); Collection<?>
	 * mimeTypes = MimeUtil.getMimeTypes(contenuFichier);
	 * 
	 * 
	 * Si le fichier est bien une image, alors son en-tête MIME commence par la
	 * chaîne "image"
	 * 
	 * if (mimeTypes.toString().startsWith("image")) { Écriture du fichier sur
	 * le disque ecrireFichier(contenuFichier, nomFichier, chemin); } else {
	 * throw new FormValidationException(
	 * "Le fichier envoyé doit être une image."); } } } catch
	 * (IllegalStateException e) {
	 * 
	 * Exception retournée si la taille des données dépasse les limites définies
	 * dans la section <multipart-config> de la déclaration de notre servlet
	 * d'upload dans le fichier web.xml
	 * 
	 * e.printStackTrace(); throw new FormValidationException(
	 * "Le fichier envoyé ne doit pas dépasser 1Mo."); } catch (IOException e) {
	 * 
	 * Exception retournée si une erreur au niveau des répertoires de stockage
	 * survient (répertoire inexistant, droits d'accès insuffisants, etc.)
	 * 
	 * e.printStackTrace(); throw new FormValidationException(
	 * "Erreur de configuration du serveur."); } catch (ServletException e) {
	 * 
	 * Exception retournée si la requête n'est pas de type multipart/form-data.
	 * 
	 * e.printStackTrace(); throw new FormValidationException(
	 * "Ce type de requête n'est pas supporté, merci d'utiliser le formulaire prévu pour envoyer votre fichier."
	 * ); }
	 * 
	 * return nomFichier; }
	 */

	/*
	 * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	 */
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