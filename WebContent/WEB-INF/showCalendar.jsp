<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>MealPlanner - Calendar</title>
               <link type="text/css" rel="stylesheet" href="<c:url value="inc/css/bootstrap.css"/>" />
        <script src="inc/js/jquery-1.12.2.min.js"></script>
		<script src="inc/js/bootstrap.min.js"></script>
   <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body>
    
         <c:import url="/inc/menu.jsp" />
        <div>
        <form method="post" action="<c:url value="/showCalendar"/>" enctype="multipart/form-data">
                    <c:import url="/inc/calendar.jsp" />
                <p class="info">${ form.resultat }</p>
                <div class="buttons">
                <input type="submit" class="btn btn-default btn-lg" value="Create grocery list"  />
            </div>
            </form>
        </div>
    </body>
</html>