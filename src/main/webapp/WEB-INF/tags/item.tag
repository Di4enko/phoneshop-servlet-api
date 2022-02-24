<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="order" required="true" type="com.es.phoneshop.model.order.Order"%>
<%@ attribute name="error" required="true" %>

<tr>
    <td>${label}<span style="color:red">*</span></td>
    <td>
      <c:set var ="error" value="${errors[name]}"/>
        <input name="${name}" value="${not empty error ? param[name] : order[name]}"/>
         <c:if test = "${not empty errors[name]}">
              <div class="error">
                  ${errors[name]}
              </div>
         </c:if>
    </td>
</tr>