<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name = "sort" required = "true" %>

<a style="text-decoration: none; color: ${sort eq param.sort and param.order eq 'asc' ? '#ff0000' : '#000000'}" href = "?query=${param.query}&sort=${sort}&order=asc">&darr;</a>
<a style="text-decoration: none; color: ${sort eq param.sort and param.order eq 'desc' ? '#ff0000' : '#000000'}" href = "?query=${param.query}&sort=${sort}&order=desc">&uarr;</a>
