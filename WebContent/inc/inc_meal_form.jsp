<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="form-group">
<label for="typeMeal">Meal Type</label>
<select class="form-control" id="typeMeal" name="typeMeal">
<option id="Breakfast" value="Breakfast">Breakfast</option>
<option id="Lunch" value="Lunch" selected>Lunch</option>
<option id="Dinner" value="Dinner">Dinner</option>
<option id="Other" value="Other">Other</option>
</select>
<span class="erreur">${form.erreurs['typeMeal']}</span>
</div>

<div class="form-group">
<label for="idDish">Dish<span class="mandatory">*</span></label>
<select class="form-control" id="idDish" name='idDish'>    
    <c:forEach items="${dishes}" var="dish">
        <option id="${dish.key}" value="${dish.key}">${dish.value}</option>
    </c:forEach>
</select>
<span class="erreur">${form.erreurs['idDish']}</span>
</div>

