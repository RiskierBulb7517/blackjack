<%@page import="com.seba.blackjack.architecture.dao.DAOException"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="cdn.html"%>
<title>Error page</title>
<jsp:include page="navbar.jsp" />
<style>
    body {
        background: linear-gradient(to right, #2c3e50, #3498db);
        color: #fff;
        min-height: 100vh;
    }
</style>
</head>
<body>
	<div class="container d-flex justify-content-center align-items-center" style="min-height: 90vh;">
		<div class="card bg-danger bg-opacity-25 text-white shadow-lg p-4 rounded-4 w-100" style="max-width: 700px;">
			<div class="card-body">
				<h3 class="card-title text-center mb-4">⚠️ Si è verificato un errore</h3>

				<%
				if (exception instanceof ClassNotFoundException) {
				%>
					<h5>Tipo eccezione: <%=exception.getClass().getName()%></h5>
					<p>Classe da caricare non trovata.</p>
					<p><strong>Messaggio:</strong> <%=exception.getMessage()%></p>

				<% } else if (exception instanceof DAOException) { %>
					<h5>Tipo eccezione: <%=exception.getClass().getName()%></h5>
					<p>Errore nella gestione del DAO.</p>
					<p><strong>Messaggio:</strong> <%=exception.getMessage()%></p>

				<% } else { %>
					<h5>Eccezione non prevista</h5>
					<p><strong>Tipo eccezione:</strong> <%=exception.getClass().getName()%></p>
					<p><strong>Messaggio:</strong> <%=exception.getMessage()%></p>
					<hr class="bg-light">
					<p><strong>StackTrace:</strong></p>
					<pre style="max-height: 300px; overflow-y: auto;"><%
						exception.printStackTrace(new PrintWriter(out));
					%></pre>
				<% } %>

				<div class="text-center mt-4">
					<button onclick="window.history.back()" class="btn btn-outline-light">
						<i class="bi bi-arrow-left"></i> Torna indietro
					</button>
				</div>
			</div>
		</div>
	</div>

</body>
</html>