<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product details">
  <p>
  ${cart}
  </p>
  <c:if test = "${not empty error}">
      <p class="error">
          ${error}
      </p>
  </c:if>
  <c:if test = "${empty error}">
      <p class="success">
        ${param.success}
      </p>
  </c:if>
  <p>
    ${product.description}
  </p>
  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td>
          <img src="${product.imageUrl}">
        </td>
      </tr>
      <tr>
        <td>Price</td>
        <td align = center>
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
      </tr>
      <tr>
        <td>Code</td>
        <td align = center>
          ${product.code}
        </td>
      </tr>
      <tr>
        <td>Stock</td>
        <td align = center>
          ${product.stock}
        </td>
      </tr>
    </thead>
  </table>
  <p>
    <form method = "post">
        <input name = "quantity" value = "${not empty error ? param.quantity : 1}" class="quantity">
        <button>Add to cart</button>
    </form>
    <c:if test = "${not empty error}">
        <p class="error">
            ${error}
        </p>
    </c:if>
  </p>
</tags:master>