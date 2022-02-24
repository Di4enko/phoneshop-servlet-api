<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="Checkout">
<form method="post">
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
            <a style="color:#000000; " href = "${pageContext.servletContext.contextPath}/products/${item.product.id}">${item.product.description}</a>
        </td>
        <td class="price">
          <a style="color:#000000; " href = "${pageContext.servletContext.contextPath}/products/pricehistory/${item.product.id}">
          <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${item.product.currency.symbol}"/></a>
        </td>
        <td>
            ${item.quantity}
        </td>
      </tr>
    </c:forEach>
  </table>
  </p>
  <p>Total quantity: ${order.totalQuantity}</p>
    <p>Total cost if goods: <fmt:formatNumber type="currency" currencySymbol="${order.items[0].product.currency.symbol}" value="${order.subtotal}"/></p>
    <p>Delivery cost: <fmt:formatNumber type="currency" currencySymbol="${order.items[0].product.currency.symbol}" value="${order.deliveryCost}"/></p>
    <p>Total cost: <fmt:formatNumber type="currency" currencySymbol="${order.items[0].product.currency.symbol}" value="${order.totalCost}"/></p>
  <p>
    <table>
        <tags:item label="First name" name="firstName" order="${order}" error="${errors}"></tags:item>
        <tags:item label="Last name" name="lastName" order="${order}" error="${errors}"></tags:item>
        <tags:item label="Phone" name="phone" order="${order}" error="${errors}"></tags:item>
        <tr>
            <td>
            Delivery date
            </td>
            <td>
            <div class="nav">
               <tags:date name="day" order="${order}" error="${errors}"></tags:date>
               <tags:date name="month" order="${order}" error="${errors}"></tags:date>
               <tags:date name="year" order="${order}" error="${errors}"></tags:date>
            </div>
                <c:set var="error" value="${errors['deliveryDate']}"/>
                <c:if test = "${not empty error}">
                    <div class="error">
                        ${error}
                    </div>
                </c:if>
            </td>
        </tr>
        <tags:item label="Delivery address" name="deliveryAddress" order="${order}" error="${errors}"></tags:item>
        <tr>
            <td>Payment method<span style="color:red">*</span></td>
            <td>
                <select name="paymentMethod">
                    <c:forEach var="paymentMethod" items="${paymentMethod}">
                        <c:if test="${param.paymentMethod != paymentMethod}">
                            <option>${paymentMethod}</option>
                        </c:if>
                        <c:if test="${param.paymentMethod == paymentMethod}">
                            <option selected>${paymentMethod}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <c:set var="error" value="${errors['paymentMethod']}"/>
                <c:if test = "${not empty error}">
                    <div class="error">
                        ${error}
                    </div>
                </c:if>
            </td>
        </tr>
    </table>
  </p>
  <p><button>Place order</button></p>
  </form>
</tags:master>