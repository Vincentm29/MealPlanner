<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="form-group">
<label for="idIngredient">Ingredient <span class="mandatory">*</span></label>
<select class="form-control" id="idIngredient" name='idIngredient'>    
    <c:forEach items="${ingredients}" var="ingredient">
        <option id="${ingredient.key}" value="${ingredient.key}">${ingredient.value.name}</option>
    </c:forEach>
</select>
<span class="erreur">${form.erreurs['idIngredient']}</span>
</div>


<div class="form-group">
<label for="quantityIngredient">Quantity</label>
<input class="form-control" type="number" id="quantityIngredient" name="quantityIngredient" value="<c:out value="${dishIngredient.quantity}"/>" size="30" maxlength="60" />
<span class="erreur">${form.erreurs['quantityIngredient']}</span>
</div>

<div class="form-group">
<label for="unitIngredient">Unit </label>
<select class="form-control" id="unitIngredient" name="unitIngredient">
<option id="grams" value="grams">Grams</option>
<option id="numberOfItem" value="numberOfItem">Number of</option>
<option id="cL" value="cL">cL</option>
</select>
<%-- <input class="form-control" type="text" id="unitIngredient" name="unitIngredient" value="<c:out value="${ingredient.priceUnit}"/>" size="30" maxlength="60" /> --%>
<span class="erreur">${form.erreurs['unitIngredient']}</span>
</div>

<div class="form-group">
<label for="commentIngredient">Comment</label>
<input class="form-control" type="text" id="commentIngredient" name="commentIngredient" value="<c:out value="${dishIngredient.comment}"/>" size="30" maxlength="60" />
<span class="erreur">${form.erreurs['commentIngredient']}</span>
</div>