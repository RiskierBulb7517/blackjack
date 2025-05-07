<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="../error.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="cdn.html"%>
<title>Blackjack Menu</title>
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
	<div class="container d-flex justify-content-center">
		<%
		if (session.getAttribute("username") == null) {
		%>

		<h1 class="display-4 fw-bold mb-4 animate__animated animate__fadeInDown" >Buongiorno, benvenuto in Black Jack!</h1>
		<div class="p-4 rounded">
			<a class="btn btn-primary btn-lg" href="login.jsp">
			    <i class="bi bi-box-arrow-in-right me-2"></i>Accedi
			</a>
		</div>
		<div class="p-4 rounded">
			<a class="btn btn-success btn-lg" href="signin.jsp">
			    <i class="bi bi-person-plus me-2"></i>Registrati
			</a>
		</div>
		<%
		} else {
		String username = (String) session.getAttribute("username");
		%>
		<h1 class="display-4 fw-bold mb-4 animate__animated animate__fadeInDown">
			Benvenuto in Black Jack,
			<%=username%></h1>
		<div class="p-4 rounded" style="max-width: 500px;">
			<a class="btn btn-primary btn-lg" href="account.jsp">Pagina dell'account
				<i class="bi bi-person me-2"></i>
			</a>
		</div>
		<div class="p-4 rounded" style="max-width: 500px;">
			<a class="btn btn-success btn-lg" href="game.jsp">Gioca <i
				class="bi bi-play me-2"></i></a>
		</div>
		<%
		}
		%>
		

		
	</div>
			<div class="container my-5 text-center text-white">
		    <div class="p-4 rounded-4 shadow-lg" style="background-color: rgba(0, 0, 0, 0.6); max-width: 800px; margin: 0 auto;">
		        <h2 class="mb-3">Sfida la fortuna, sfida il banco</h2>
		        <p class="lead">
		            Black Jack è il classico gioco di carte dove l'astuzia, il calcolo e un pizzico di audacia possono farti vincere tutto... o perdere tutto. <br>
		            Registrati, accedi con il tuo account e inizia la partita contro il banco. Riuscirai a raggiungere 21 senza sballare?
		        </p>
		    </div>
		</div>
</body>
</html>