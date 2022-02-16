<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<header>
    <a href="${pageContext.servletContext.contextPath}">
        <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
        PhoneShop
    </a>
    <a href="${pageContext.servletContext.contextPath}/cart">
    <jsp:include page="/cart/minicart"/>
    </a>
</header>