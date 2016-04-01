<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Dishes list</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="inc/css/bootstrap.css"/>" />
        <script src="inc/js/jquery-1.12.2.min.js"></script>
		<script src="inc/js/bootstrap.min.js"></script>
   <meta name="viewport" content="width=device-width, initial-scale=1">
   </head>
    <body>
        <c:import url="/inc/menu.jsp" />
        <div id="corps">
        <c:choose>
            <%-- Si aucun client n'existe en session, affichage d'un message par défaut. --%>
            <c:when test="${ empty sessionScope.dishes }">
                <p class="erreur">No dish to show</p>
            </c:when>
            <%-- Sinon, affichage du tableau. --%>
            <c:otherwise>
            <div class="table-responsive">
            <table class="table table-striped">
                <tr>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Number of People</th>
                    <th>Cooking Time</th>
                    <th>Preparation Time</th>
                    <th class="action">Action</th>                    
                </tr>
                <%-- Parcours de la Map des clients en session, et utilisation de l'objet varStatus. --%>
                <c:forEach items="${ sessionScope.dishes }" var="mapDishes" varStatus="boucle">
                   <tr>    
                    <td><c:out value="${ mapDishes.value.name }"/></td>
                    <td><c:out value="${ mapDishes.value.category }"/></td>
                    <td><c:out value="${ mapDishes.value.numberOfPeople }"/></td>
                    <td><c:out value="${ mapDishes.value.cookingTime }"/></td>
                    <td><c:out value="${ mapDishes.value.preparationTime }"/></td>
                    
                    <%-- Lien vers la servlet de suppression, avec passage du nom du client - c'est-à-dire la clé de la Map - en paramètre grâce à la balise <c:param/>. --%>
                    <td class="action">
                        <a href="<c:url value="/editDish"><c:param name="id" value="${ mapDishes.key }" /></c:url>">
                            <img src="<c:url value="/inc/edit.png"/>" alt="Edit" />
                        </a>
                    </td>
                </tr>
                </c:forEach>
            </table>
            </div>
            </c:otherwise>
        </c:choose>
	        <div class="buttons">
	        	<a class="btn btn-default" href="./addDish" role="button">Add Dish</a>
	        </div>
        </div>
    </body>
</html>