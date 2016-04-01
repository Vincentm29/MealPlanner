<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>List of Ingredients for ${sessionScope.dish.name}</title>
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
            <c:when test="${ empty sessionScope.dishingredients }">
                <p class="erreur">No ingredient to show</p>
            </c:when>
            <%-- Sinon, affichage du tableau. --%>
            <c:otherwise>
             <div class="form1">
            <h2>List of Ingredients for ${sessionScope.dish.name}</h2>
            </div>
            <div class="table-responsive">
            <table class="table table-striped">
                <tr>
                    <th>Name</th>
                    <th>Quantity</th>
                    <th class="action">Action</th>                    
                </tr>
                <%-- Parcours de la Map des clients en session, et utilisation de l'objet varStatus. --%>
                <c:forEach items="${ sessionScope.dishingredients }" var="vdishIngredient" varStatus="boucle">
                <%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
                <%-- ><tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">--%
                
                    <%-- Affichage des propriétés du bean Client, qui est stocké en tant que valeur de l'entrée courante de la map --%>
                <tr>    
                    <td><c:out value="${ vdishIngredient.ingredient.name }"/></td>
                    <td><c:out value="${ vdishIngredient.quantity} "/>
                    	<c:if test="${vdishIngredient.unit!='numberOfItem'}">
                    	<c:out value="${ vdishIngredient.unit}"/>
						</c:if>
					</td>
                    
                    <%-- Lien vers la servlet de suppression, avec passage du nom du client - c'est-à-dire la clé de la Map - en paramètre grâce à la balise <c:param/>. --%>
                    <td class="action">
                        <a href="<c:url value="/suppressionClient"><c:param name="idClient" value="${ vdishIngredient.id }" /></c:url>">
                            <img src="<c:url value="/inc/supprimer.png"/>" alt="Supprimer" />
                        </a>
                    </td>
                </tr>
                </c:forEach>
            </table>
            </div>
            </c:otherwise>
        </c:choose>
	        <div class="buttons">
<a class="btn btn-default" href="<c:url value="/addDishIngredient"><c:param name="id" value="${ sessionScope.dish.id }" /></c:url>" role="button">Add Ingredient</a>
	        </div>
        </div>
    </body>
</html>