<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>MealPlanner - Add Dish</title>
               <link type="text/css" rel="stylesheet" href="<c:url value="inc/css/bootstrap.css"/>" />
        <script src="inc/js/jquery-1.12.2.min.js"></script>
		<script src="inc/js/bootstrap.min.js"></script>
   <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body>
        <c:import url="/inc/menu.jsp" />
        <div class="form1">
            <form method="post" action="<c:url value="/addDish"/>" enctype="multipart/form-data">
                <fieldset>
                    <legend>Dish information</legend>
                    <c:import url="/inc/inc_dish_form.jsp" />
                </fieldset>  
                <p class="info">${ form.resultat }</p>
                <div class="buttons">
                <input type="submit" class="btn btn-primary btn-lg" value="Add"  />
                <input type="reset" class="btn btn-default btn-lg" value="Clear" /> <br />
            </div>
            </form>
        </div>
    </body>
</html>