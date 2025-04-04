<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="cdn.html"%>
<title>Registrati</title>
<jsp:include page="navbar.jsp" />
</head>
<body>
	<div class="container">
		<header class="page-header">
			<h2>Inserisci i dati per la registrazione</h2>
		</header>
		<br>
		<form action="/<%=application.getServletContextName()%>/registra"
			method="post" class="form-horizontal">
			<div class="mb-1 row">
				<label for="username" class="col-form-label">Username</label>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-text"><i class="bi bi-person"></i></span>
					<input type="text" name="username" id="username"
						class="form-control" placeholder="il tuo username..." required>
				</div>
			</div>
<br>
			<div class="mb-1 row">
				<label for="email" class="col-form-label">Email</label>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-text"><i class="bi bi-envelope"></i></span>
					<input type="email" name="email" id="email" class="form-control"
						placeholder="la tua email..." required>
				</div>
			</div>
<br>
			<div class="mb-1 row">
				<label for="password" class="col-form-label">Password</label>
			</div>
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-text"><i class="bi bi-lock"></i></span> <input
						type="password" name="password" id="password" class="form-control"
						placeholder="la tua password..." required>
				</div>
			</div>
			<div class="mt-3">
				<div class="col-sm-6">
					<button type="submit" class="btn btn-primary">
						Registrati <i class="bi bi-send"></i>
					</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>