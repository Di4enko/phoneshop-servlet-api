<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="Overview">
<p>
  <table>
    <thead>
      <tr>
        <td align = center>Image</td>
        <td align = center>Description</td>
        <td align = center>Price</td>
        <td align = center>Quantity</td>
      </tr>
    </thead>
    <c:forEach var="item" items="${order.items}" varStatus="status">
      <tr>
        <td>
          <img class="product-tile" src="${item.product.imageUrl}">
        </td>
        <td>
            ${item.product.description}
        </td>
        <td class="price">
          <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${item.product.currency.symbol}"/></a>
        </td>
        <td>
            ${item.quantity}
        </td>
      </tr>
    </c:forEach>
  </table>
    <p>Total cost if goods: <fmt:formatNumber type="currency" currencySymbol="${order.items[0].product.currency.symbol}" value="${order.subtotal}"/></p>
    <p>Delivery cost: <fmt:formatNumber type="currency" currencySymbol="${order.items[0].product.currency.symbol}" value="${order.deliveryCost}"/></p>
    <p>Total cost: <fmt:formatNumber type="currency" currencySymbol="${order.items[0].product.currency.symbol}" value="${order.totalCost}"/></p>
  <p>
  <h2>Your details</h2>
    <table>
        <tr><td>First name</td><td>${order.firstName}</td></tr>
        <tr><td>Last name</td><td>${order.lastName}</td></tr>
        <tr><td>Phone</td><td>${order.phone}</td></tr>
        <tr><td>Delivery date</td><td>${order.deliveryDate}</td></tr>
        <tr><td>Delivery address</td><td>${order.deliveryAddress}</td></tr>
        <tr><td>Payment method</td><td>${order.paymentMethod}</td></tr>
    </table>
 </p>
</tags:master>