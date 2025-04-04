<!-- blackjack.jsp senza JSTL -->
<%@page import="com.seba.blackjack.bc.ImmagineBC"%>
<%@page import="com.seba.blackjack.bc.model.Immagine"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.seba.blackjack.utils.PartitaSessionBean, com.seba.blackjack.bc.model.Carta, java.util.List" %>
<jsp:useBean id="partitaBean" class="com.seba.blackjack.utils.PartitaSessionBean" scope="session" />
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
	String username = (String) session.getAttribute("username");
	partitaBean.inizializzaPartita(username);

    List<Carta> manoUser = partitaBean.getManoUser();
    List<Carta> manoBot = partitaBean.getManoBot();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Blackjack</title>
    <%@ include file="cdn.html"%>
    <style>
        body { background-color: #0b3d91; color: white; text-align: center; }
        .card-container { display: flex; justify-content: center; gap: 10px; margin-top: 20px; }
        .card-img { width: 80px; height: auto; transition: transform 0.3s ease; }
        .card-img:hover { transform: scale(1.1); }
    </style>
    <jsp:include page="navbar.jsp" />
</head>
<body>
    <div class="container mt-5 p-4">
        <h1>Benvenuto, <%= session.getAttribute("username") %></h1>
        <h2>Blackjack</h2>

        <h3>Mano del Banco</h3>
        <div class="card-container">
            <% for (Carta carta : manoBot) { 
            		Immagine imm = ImmagineBC.getUBC().getImmByCartaID(carta.getId());%>
                <img class="card-img" src="<%= imm.getUrl() %>" alt="carta" />
            <% } %>
        </div>

        <h3>La tua Mano</h3>
        <div class="card-container">
            <% for (Carta carta : manoUser) { 
                     Immagine imm = ImmagineBC.getUBC().getImmByCartaID(carta.getId());%>
                <img class="card-img" src="<%= imm.getUrl() %>" alt="carta" />
            <% } %>
        </div>

        <h4 class="mt-4">Punteggio Utente: <%= partitaBean.getPunteggioGiocatore() %></h4>
        <h4>Punteggio Banco: <%= partitaBean.getPunteggioBanco() %></h4>

        <div class="mt-4">
            <form method="post" action="/<%=application.getServletContextName()%>game">
                <button type="submit" name="azione" value="hit" class="btn btn-success btn-lg me-2">Hit</button>
                <button type="submit" name="azione" value="stand" class="btn btn-warning btn-lg">Stand</button>
            </form>
        </div>

        <% if (partitaBean.isPartitaFinita()) { %>
            <div class="alert alert-danger mt-4">La partita è finita! <a href="/<%=application.getServletContextName()%>game?azione=nuova">Gioca di nuovo</a></div>
        <% } %>
    </div>
</body>
</html>

