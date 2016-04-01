<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="form-group">
<label for="nameDish">Name</label>
<input class="form-control" type="text" id="nameDish" name="nameDish" value="<c:out value="${dish.name}"/>" size="30" maxlength="60" />
<span class="erreur">${form.erreurs['nameDish']}</span>
</div>

<div class="form-group">
<label for="categoryDish">Category</label>
<select class="form-control" id="categoryDish" name="categoryDish">
<option id="Appetizer" value="Appetizer">Appetizer</option>
<option id="Entree" value="Entree" selected>Entree</option>
<option id="Dessert" value="Dessert">Dessert</option>
<option id="Breakfast" value="Breakfast">Breakfast</option>
<option id="Other" value="Other">Other</option>
</select>
<span class="erreur">${form.erreurs['categoryDish']}</span>
</div>


<div class="form-group">
<label for="nbpeopleDish">Number of People</label>
<input class="form-control" type="number" id="nbpeopleDish" name="nbpeopleDish" value="<c:out value="${dish.numberOfPeople}"/>" size="30" maxlength="60" />
<span class="erreur">${form.erreurs['nbpeopleDish']}</span>
</div>

<div class="form-group">
<label for="cookingtimeDish">Cooking Time</label>
<input class="form-control" type="number" id="cookingtimeDish" name="cookingtimeDish" value="<c:out value="${dish.cookingTime}"/>" size="30" maxlength="60" />
<span class="erreur">${form.erreurs['cookingtimeDish']}</span>
</div>

<div class="form-group">
<label for="preptimeDish">Preparation Time</label>
<input class="form-control" type="number" id="preptimeDish" name="preptimeDish" value="<c:out value="${dish.preparationTime}"/>" size="30" maxlength="60" />
<span class="erreur">${form.erreurs['preptimeDish']}</span>
</div>

<div class="form-group">
<label for="pricerangeDish">Price Range</label>
<input class="form-control" type="text" id="pricerangeDish" name="pricerangeDish" value="<c:out value="${dish.priceRange}"/>" size="30" maxlength="60" />
<span class="erreur">${form.erreurs['pricerangeDish']}</span>
</div>