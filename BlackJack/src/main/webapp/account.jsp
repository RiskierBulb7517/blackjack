<%@page import="com.seba.blackjack.bc.model.PartitaPC"%>
<%@page import="com.seba.blackjack.bc.PartitaPCBC"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%
String username = "";
if (session.getAttribute("username") != null) {
	username = (String) session.getAttribute("username");
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="cdn.html"%>
<title>Profilo: <%=username%></title>
<jsp:include page="navbar.jsp" />
<script src="js/datatable.js"></script>
</head>
<body>

	<div class="container">
		<h2 class="mb-3">Partite giocate</h2>
		<%
		PartitaPC[] partite = PartitaPCBC.getPBC().getPartiteByUsername(username);
		%>

		<table class="table table-bordered">
			<thead class="table-dark">
				<tr>
					<th>Stato</th>
					<th>Punti Banco</th>
					<th>Punti Utente</th>
					<th></th>
				</tr>
			</thead>
			<tbody>

				<%
				for (int i = 0; i < partite.length; i++) {
					PartitaPC partita = partite[i];
				%>
				<tr style="vertical-align: middle;">
					<td><%=partita.getStato()%></td>
					<td><%=partita.getPuntibanco()%></td>
					<td><%=partita.getPuntiutente()%></td>
					<td>
						<form method="POST"
							action="/<%=application.getServletContextName()%>/delete?id=<%=partita.getId()%>">
								<button class="btn btn-danger btn-sm" type="submit"><i class="bi bi-trash"></i></button>
						</form>
						</td>
				</tr>
				<%
				}
				%>
			</tbody>

		</table>


	</div>

</body>
</html>
<%
} else {
response.sendRedirect("login.jsp");
}
%>