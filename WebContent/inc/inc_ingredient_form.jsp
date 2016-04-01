<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="form-group">
<label for="nameIngredient">Name <span class="mandatory">*</span></label>
<input class="form-control" type="text" id="nameIngredient" name="nameIngredient" value="<c:out value="${ingredient.name}"/>" size="30" maxlength="30" />
<span class="erreur">${form.erreurs['nameIngredient']}</span>
</div>

<!-- <div class="form-group"> -->
<!-- <label for="categoryIngredient">Category </label> -->
<%-- value="<c:out value="${ingredient.categoryId}"/>" --%>
<!-- <select class="form-control" id="categoryIngredient" name="categoryIngredient"> -->
<!--       <option>1</option> -->
<!--       <option>2</option> -->
<!--       <option>3</option> -->
<!--       <option>4</option> -->
<!--       <option>5</option> -->
<!--     </select> -->
<%-- <span class="erreur">${form.erreurs['categoryIngredient']}</span> --%>
<!-- </div> -->

<div class="form-group">
<label for="categoryIngredient">Category <span class="mandatory">*</span></label>
<select class="form-control" id="categoryIngredient" name='categoryIngredient'>    
    <c:forEach items="${Categories}" var="Category">
        <option id="${Category.key}" value="${Category.key}">${Category.value}</option>
    </c:forEach>
</select>
<span class="erreur">${form.erreurs['categoryIngredient']}</span>
</div>


<div class="form-group">
<label for="priceIngredient">Price </label>
<input class="form-control" type="number" id="priceIngredient" name="priceIngredient" value="<c:out value="${ingredient.price}"/>" size="30" maxlength="60" />
<span class="erreur">${form.erreurs['priceIngredient']}</span>
</div>

<div class="form-group">
<label for="unitIngredient">Unit </label>
<select class="form-control" id="unitIngredient" name="unitIngredient">
<option></option>
<option id="perLb" value="lb">Per lb</option>
<option id="numberOfItem" value="numberOfItem">Per item</option>
</select>
<%-- <input class="form-control" type="text" id="unitIngredient" name="unitIngredient" value="<c:out value="${ingredient.priceUnit}"/>" size="30" maxlength="60" /> --%>
<span class="erreur">${form.erreurs['unitIngredient']}</span>
</div>