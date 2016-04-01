<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="form-group">
<label for="nameCategory">Name <span class="mandatory">*</span></label>
<input class="form-control" type="text" id="nameCategory" name="nameCategory" value="<c:out value="${category.name}"/>" size="30" maxlength="30" />
<span class="erreur">${form.erreurs['nameCategory']}</span>
</div>