<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name = "sort" required = "true" %>

<a style="text-decoration: none; color: ${sort eq param.sort and param.order eq 'ASC' ? '#ff0000' : '#000000'}" href = "?query=${param.query}&sort=${sort}&order=ASC">&darr;</a>
<a style="text-decoration: none; color: ${sort eq param.sort and param.order eq 'DESC' ? '#ff0000' : '#000000'}" href = "?query=${param.query}&sort=${sort}&order=DESC">&uarr;</a>
