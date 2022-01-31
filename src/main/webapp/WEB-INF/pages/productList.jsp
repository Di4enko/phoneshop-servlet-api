<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
  <p>
    Welcome to Expert-Soft training!
  </p>
  <form>
    <input name = "query", value = "${param.query}">
    <button>Search</button>
  </form>
  <table>
    <thead>
      <tr>
        <td align = center>Image</td>
        <td align = center>
            Description
            <tags:sortLink sort = "description"/>
        </td>
        <td align = center>
            Price
            <tags:sortLink sort = "price"/>
        </td>
      </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src="${product.imageUrl}">
        </td>
        <td>
            <a style="color:#000000; " href = "${pageContext.servletContext.contextPath}/products/${product.id}?id=${product.id}">${product.description}
        </td>
        <td class="price">
          <a style="color:#000000; " href = "/WEB-INF/pages/priceHistory.jsp">
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
      <div id="priceHistory">
         <h1>Price history<h1>
         <h2><h2>
         <table>
             <thead>
               <tr>
                 <td align = left>
                     Start date
                 </td>
                 <td align = left>
                     Price
                 </td>
               </tr>
             </thead>
              <c:forEach var="priceHistory" items="${product.priceHistory}">
                <tr>
                  <td>
                    "${priceHistory.date}"
                  </td>
                  <td>
                   "${priceHistory.price}"
                  </td>
                </tr>
              </c:forEach>
           </table>
      </div>
</tags:master>