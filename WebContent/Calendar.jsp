<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

            <div class="table-responsive">
            <table class="table table-striped">
  <tr>
  <c:forEach items="${ sessionScope.week }" var="mapDay" varStatus="boucle">
    <td><joda:format value="${ mapDay.value.date }" pattern="E dd MMMM YYYY" /></td>
    </c:forEach>
  </tr>
  <tr>
  <c:forEach items="${ sessionScope.week }" var="mapDay" varStatus="boucle">
    <td><a class="btn btn-default" href="<c:url value="/addMeal">
	<joda:format var = "date" value="${ mapDay.value.date }" pattern="YYYYMMdd" />
    <c:param name="date" value="${mapDay.key }" />
    </c:url>" role="button">Add Meal
    </a>
    </td>
    </c:forEach>
  </tr>
</table>
</div>

                