<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<tags:master pageTitle="Cart">
  <p>
  ${cart}
  </p>
  <c:if test = "${not empty errors}">
      <p class="error">
          There were errors updating cart
      </p>
  </c:if>
  <c:if test = "${empty errors}">
      <p class="success">
        ${param.success}
      </p>
  </c:if>
<form method="post">
  <table>
    <thead>
      <tr>
        <td align = center>Image</td>
        <td align = center>Description</td>
        <td align = center>Price</td>
        <td align = center>Quantity</td>
        <td></td>
      </tr>
    </thead>
    <c:forEach var="cartItem" items="${cart.items}" varStatus="status">
      <tr>
        <td>
          <img class="product-tile" src="${cartItem.product.imageUrl}">
        </td>
        <td>
            <a style="color:#000000; " href = "${pageContext.servletContext.contextPath}/products/${cartItem.product.id}">${cartItem.product.description}</a>
        </td>
        <td class="price">
          <a style="color:#000000; " href = "${pageContext.servletContext.contextPath}/products/pricehistory/${cartItem.product.id}">
          <fmt:formatNumber value="${cartItem.product.price}" type="currency" currencySymbol="${cartItem.product.currency.symbol}"/></a>
        </td>
        <td>
          <c:set var ="error" value="${errors[cartItem.product.id]}"/>
            <input name="quantity" value="${not empty error ? paramValues['quantity'][status.index] : cartItem.quantity}" class="quantity"/>
             <c:if test = "${not empty errors[cartItem.product.id]}">
                  <p class="error">
                      ${errors[cartItem.product.id]}
                  </p>
             </c:if>
          <input type="hidden" name="productID" value="${cartItem.product.id}"/>
        </td>
        <td>
        <button form="deleteCartItem"
                formaction="${pageContext.servletContext.contextPath}/cart/deleteCartItem/${cartItem.product.id}">Delete</button>
        </td>
      </tr>
    </c:forEach>
  </table>
  <c:if test="${not empty cart.items}">
  <p>
    <button>Update</button>
  </p>
  </c:if>
</form>
<form id="deleteCartItem" method="post"></form>
<c:if test="${not empty cart.items}">
<a href="${pageContext.servletContext.contextPath}/checkout">
        <button>Checkout</button>
    </a>
</c:if>
</tags:master>