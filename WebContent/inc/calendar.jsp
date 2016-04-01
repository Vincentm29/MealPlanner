<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

            <div class="table-responsive">
            <table class="table table-striped">
  <tr>
  <td>Date</td>
  <c:forEach items="${ sessionScope.week }" var="mapDay" varStatus="boucle">
    <td><joda:format value="${ mapDay.value.date }" pattern="E dd MMMM YYYY" /></td>
    </c:forEach>
    </tr>
	<tr><td>Breakfast</td>
	    <c:forEach items="${ sessionScope.week }" var="mapDay" varStatus="boucle">
	    		<td>	    		        
		    <c:forEach items="${ mapDay.value.listMeal }" var="meal" varStatus="boucle">  
			    
			    <c:choose>
							
				    <c:when test="${ meal.mealType == 'Breakfast'}">

						<c:forEach items="${ meal.listDishes }" var="dish" varStatus="boucle">
						<c:out value="${ dish.name}"> </c:out>
				    	</c:forEach>
				    	
				    </c:when>

			    </c:choose>
			    	
		    </c:forEach>
	</td>
	    </c:forEach>
	 </tr>
	 	<tr><td>Lunch</td>
	    <c:forEach items="${ sessionScope.week }" var="mapDay" varStatus="boucle">
	    		<td>	    		        
		    <c:forEach items="${ mapDay.value.listMeal }" var="meal" varStatus="boucle">  
			    <c:choose>
	
				    <c:when test="${ meal.mealType == 'Lunch'}">
	
						<c:forEach items="${ meal.listDishes }" var="dish" varStatus="boucle">
							<c:out value="${ dish.name}"> </c:out>
				    	</c:forEach>
				    </c:when>
	
			    </c:choose>
			    
		    </c:forEach>
		</td>
	    </c:forEach>
	 </tr>
	 	<tr><td>Other</td>
	    <c:forEach items="${ sessionScope.week }" var="mapDay" varStatus="boucle">
	    		<td>	    		        
		    <c:forEach items="${ mapDay.value.listMeal }" var="meal" varStatus="boucle">  
			    
			    <c:choose>
	
				    <c:when test="${ meal.mealType == 'Other'}">
	
						<c:forEach items="${ meal.listDishes }" var="dish" varStatus="boucle">
							<c:out value="${ dish.name}"> </c:out>
				    	</c:forEach>
				    </c:when>
	
			    </c:choose>

		    </c:forEach>
		</td>
	    </c:forEach>
	 </tr>
	 	<tr><td>Dinner</td>
	    <c:forEach items="${ sessionScope.week }" var="mapDay" varStatus="boucle">
	    	<td>		    		        
		    <c:forEach items="${ mapDay.value.listMeal }" var="meal" varStatus="boucle">  

			    <c:choose>
	
				    <c:when test="${ meal.mealType == 'Dinner'}">
	
						<c:forEach items="${ meal.listDishes }" var="dish" varStatus="boucle">
							<c:out value="${ dish.name}"> </c:out>
				    	</c:forEach>
				    </c:when>
	
			    </c:choose>

		    </c:forEach>
		</td>	
	    </c:forEach>
	 </tr>
  <tr>
  <td>Action</td>
  <c:forEach items="${ sessionScope.week }" var="mapDay" varStatus="boucle">
    <td><a class="btn btn-default" href="<c:url value="/addMeal">
	<joda:format var = "date" value="${ mapDay.value.date }" pattern="YYYYMMdd" />
    <c:param name="date" value="${mapDay.key }" />
    </c:url>" role="button">Add Meal
    </a>
    </td>
    </c:forEach>

  </tr>
  <tr>
  <td>Add To List</td>
  <c:forEach items="${ sessionScope.week }" var="mapDay" varStatus="boucle">
    <td>  
  <input type="checkbox" id=dates name="dates" value="${mapDay.key }">
  <span class="c-indicator"></span>
    </td>
    </c:forEach>
  </tr>
</table>
</div>

                