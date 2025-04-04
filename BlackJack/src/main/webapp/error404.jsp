<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="cdn.html"%>
<title>Error404</title>
<jsp:include page="navbar.jsp" />
</head>
<body>
<h2>Error404 - Pagina inesistente</h2>
<br>
<a class="btn btn-primary" href="<%=application.getContextPath()%>/index.jsp">Ritorna alla home</a>
</body>
</html>