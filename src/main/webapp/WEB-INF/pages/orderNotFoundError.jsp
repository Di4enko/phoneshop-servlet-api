<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<tags:master pageTitle="Product not found">
    <h1>Sorry, your order is not find</h1>
    <p>
        <a href = "${pageContext.servletContext.contextPath}/products">Click to return to the home page</a>
    </p>
</tags:master>