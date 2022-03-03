<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="ProductsSearch">
  <h1>
    Advanced search page
  </h1>
<form method="post">
    <p>
        Product code
        <tags:findProduct name="code" error="${errors}"></tags:findProduct>
    </p>
    <p>
        Min price
        <tags:findProduct name="minPrice" error="${errors}"></tags:findProduct>
    </p>
    <p>
        Max price
        <tags:findProduct name="maxPrice" error="${errors}"></tags:findProduct>
    </p>
    <p>
        Min stock
        <tags:findProduct name="minStock" error="${errors}"></tags:findProduct>
    </p>
<button>Search</button>
</form>
<c:if test="${not empty products}">
<div class="success">
    Find products
</div>
    <table>
        <thead>
          <tr>
            <td align = center>Image</td>
            <td align = center>
                Description
            </td>
            <td align = center>
                Price
            </td>
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
            <td class="price">
              <a style="color:#000000; " href = "${pageContext.servletContext.contextPath}/products/pricehistory/${product.id}">
              <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/></a>
            </td>
          </tr>
        </c:forEach>
      </table>
</c:if>
</tags:master>