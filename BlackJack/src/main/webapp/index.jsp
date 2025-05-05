<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="../error.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="cdn.html"%>
<title>Blackjack Menu</title>
<jsp:include page="navbar.jsp" />
</head>
<body>
	<div class="container-fluid mt-2" style="text-align: center;">
		<%
		if (session.getAttribute("username") == null) {
		%>

		<h3>Buongiorno, benvenuto in Black Jack!</h3>
		<div class="container-sm my-4">
			<a class="btn btn-primary" href="login.jsp">Entra con il tuo
				account <i class="bi bi-box-arrow-in-right"></i>
			</a>
		</div>
		<div class="container-sm my-4">
			<a class="btn btn-primary" href="signin.jsp">Registra un nuovo
				account <i class="bi bi-person-plus"></i>
			</a>
		</div>
		<%
		} else {
		String username = (String) session.getAttribute("username");
		%>
		<h3>
			Benvenuto in Black Jack,
			<%=username%></h3>
		<div class="container-sm my-4">
			<a class="btn btn-primary" href="account.jsp">Pagina dell'account
				<i class="bi bi-person"></i>
			</a>
		</div>
		<div class="container-sm my-4">
			<a class="btn btn-primary" href="game.jsp">Gioca <i
				class="bi bi-play"></i></a>
		</div>
		<%
		}
		%>
	</div>
</body>
</html>