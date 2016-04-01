package com.Mealplanner.forms;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.text.WordUtils;

import com.Mealplanner.beans.Ingredient;
import com.Mealplanner.beans.IngredientCategory;
import com.Mealplanner.dao.DAOException;
import com.Mealplanner.dao.IngredientCategoryDao;
import com.Mealplanner.dao.IngredientDao;

public final class CreationIngredientForm {
	private static final String FIELD_NAME = "nameIngredient";
	private static final String FIELD_CATEGORY = "categoryIngredient";
	private static final String FIELD_PRICE = "priceIngredient";
	private static final String FIELD_UNIT = "unitIngredient";

	@EJB
	private IngredientCategoryDao categoryDao;

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();
	private IngredientDao ingredientDao;

	public CreationIngredientForm(IngredientDao ingredientDao) {
		this.ingredientDao = ingredientDao;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public String getResultat() {
		return resultat;
	}

	public Ingredient creerIngredient(HttpServletRequest request, String chemin) {
		String name = getValeurChamp(request, FIELD_NAME);
		String category = getValeurChamp(request, FIELD_CATEGORY);
		String price = getValeurChamp(request, FIELD_PRICE);
		String unit = getValeurChamp(request, FIELD_UNIT);

		Ingredient ingredient = new Ingredient();

		Long categoryid = Long.valueOf(category);
		// List<IngredientCategory> listcat = categoryDao.lister();
		// listcat.get(0);
		// IngredientCategory ingCategory = categoryDao.trouver(categoryid);

		IngredientCategory ingCategory = new IngredientCategory();
		ingCategory.setId(categoryid);
		ingredient.setCategory(ingCategory);
		ingredient.setPriceUnit(unit);

		checkName(name, ingredient);
		checkPrice(price, ingredient);
		/*
		 * traiterAdresse(adresse, client); traiterTelephone(telephone, client);
		 * traiterEmail(email, client); traiterImage(client, request, chemin);
		 */

		try {
			if (erreurs.isEmpty()) {
				ingredientDao.creer(ingredient);
				resultat = "Succès de la création de l'ingrédient.";
			} else {
				resultat = "Échec de la création de l'ingrédient.";
			}
		} catch (DAOException e) {
			setErreur("imprévu", "Erreur imprévue lors de la création.");
			resultat = "Échec de la création de l'ingrédient : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
			e.printStackTrace();
		}

		return ingredient;
	}

	private void checkName(String name, Ingredient ingredient) {
		try {
			WordUtils.capitalizeFully(name);
			validationName(name);
		} catch (FormValidationException e) {
			setErreur(FIELD_NAME, e.getMessage());
		}
		ingredient.setName(name);
	}

	private void checkPrice(String price, Ingredient ingredient) {
		double priceValue = -1;
		try {
			priceValue = validationPrice(price);
		} catch (FormValidationException e) {
			setErreur(FIELD_PRICE, e.getMessage());
		}
		ingredient.setPrice(priceValue);
	}

	/*
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
	 */
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

	private double validationPrice(String price) throws FormValidationException {
		double temp;
		if (price != null) {
			try {
				temp = Double.parseDouble(price);
				if (temp < 0) {
					throw new FormValidationException("Price must be positive.");
				}
			} catch (NumberFormatException e) {
				temp = -1;
				throw new FormValidationException("Price must be a number");
			}
		} else {
			temp = -1;
		}
		return temp;
	}

	/*
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