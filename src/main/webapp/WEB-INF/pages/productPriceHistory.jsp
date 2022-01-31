<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.Product" scope="request"/>
<tags:master pageTitle="Product details">
<h1>Price history<h1>
<p>${product.description}</p>
  <table>
    <thead>
      <tr>
        <td align = center>
            Start date
        </td>
        <td align = center>
            Price
        </td>
      </tr>
    <c:forEach var="priceHistory" items="${product.priceHistory}">
      <tr>
        <td>
          ${priceHistory.date}
        </td>
        <td class="price">
          <fmt:formatNumber value="${priceHistory.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>