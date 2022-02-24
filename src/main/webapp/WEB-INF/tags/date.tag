<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="order" required="true" type="com.es.phoneshop.model.order.Order"%>
<%@ attribute name="error" required="true" %>

<input type="text" name="${name}" value="${not empty param[name] ? param[name] : name}" size="20px" width="20px"/>
