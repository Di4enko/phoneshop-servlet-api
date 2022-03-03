<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="error" required="true" %>

<c:set var ="error" value="${errors[name]}"/>
<input name="${name}" value="${param[name]}"/>
 <c:if test = "${not empty errors[name]}">
      <div class="error">
          ${errors[name]}
      </div>
 </c:if>
