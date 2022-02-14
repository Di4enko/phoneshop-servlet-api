<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="recentlyViewed" type="com.es.phoneshop.model.browsingHistory.BrowsingHistory" scope="request"/>
<tags:master pageTitle="Product List">
  <p>
    Welcome to Expert-Soft training!
  </p>
  <form>
    <input name = "query", value = "${param.query}">
    <button>Search</button>
  </form>
  <p>
  ${cart}
  </p>
  <c:if test = "${not empty error}">
      <p class="error">
          There were errors adding to cart
      </p>
  </c:if>
  <c:if test = "${empty error}">
      <p class="success">
        ${param.success}
      </p>
  </c:if>
  <table>
    <thead>
      <tr>
        <td align = center>Image</td>
        <td align = center>
            Description
            <tags:sortLink sort = "DESCRIPTION"/>
        </td>
        <td>Quantity</td>
        <td align = center>
            Price
            <tags:sortLink sort = "PRICE"/>
        </td>
        <td></td>
      </tr>
    </thead>
    <c:forEach var="product" items="${products}" varStatus="status">
      <tr>
        <td>
          <img class="product-tile" src="${product.imageUrl}">
        </td>
        <td>
            <a style="color:#000000; " href = "${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>
        </td>
        <td>
        <a name="addToCart">
            <form method="post">
                <input name="quantity" value="${not empty error and errorID == product.id? param.quantity : 1}" class="quantity"/>
                <c:if test = "${not empty error and errorID == product.id}">
                <p class="error">
                    ${error}
                </p>
                </c:if>
                <input type="hidden" name="productID" value="${product.id}"/>
            <form>
        </a>
        </td>
        <td class="price">
          <a style="color:#000000; " href = "${pageContext.servletContext.contextPath}/products/pricehistory/${product.id}">
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/></a>
        </td>
        <a href="#addToCart">
        <td>
          <button>Add to cart</button>
        </td>
        </a>
        </form>
      </tr>
    </c:forEach>
  </table>
  <p><h3>Recently viewed:</h3></p>
  <p>
    <c:forEach var="item" items="${recentlyViewed.recentlyViewed}">
        <c:if test="${not empty item}">
          <div class="nav">
            <div class="border" align = center>
                <img class="product-tile" src="${item.imageUrl}">
                <p>
                <a style="color:#000000; " href = "${pageContext.servletContext.contextPath}/products/${item.id}">${item.description}</a>
                </p>
                <p>
                <class="price">
                <fmt:formatNumber value="${item.price}" type="currency" currencySymbol="${item.currency.symbol}"/>
                </p>
            </div>
          </div>
        </c:if>
    </c:forEach>
  </p>
</tags:master>