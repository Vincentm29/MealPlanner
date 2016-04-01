<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="index">MealPlanner</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="./showCalendar">Home</a></li>
            <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Dishes
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="showDishes">Show Dishes</a></li>
          <li><a href="addDish">Add Dish</a></li>
        </ul>
      </li>
      <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Ingredients
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="showIngredients">Show Ingredients</a></li>
          <li><a href="addIngredient">Add Ingredient</a></li>
        </ul>
      </li>
      <li class="dropdown">
      <a class="dropdown-toggle" data-toggle="dropdown" href="#">Categories
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="showIngredientCategories">Show Categories</a></li>
          <li><a href="addIngredientCategory">Add Category</a></li>
        </ul>
        </li>
      <li><a href="#">Page 3</a></li> 
    </ul>
  </div>
</nav>