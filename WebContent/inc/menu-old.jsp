<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="menu">
    <p><a href="<c:url value="/addIngredient"/>">Add Ingredient</a></p>
    <p><a href="<c:url value="/showIngredients"/>">Show Ingredients</a></p>
    <p><a href="<c:url value="/addIngredientCategory"/>">Add Category</a></p>
    <p><a href="<c:url value="/showIngredientCategories"/>">Show Categories</a></p>
</div>