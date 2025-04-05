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
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="cdn.html"%>
    <title>Blackjack</title>
    <jsp:include page="navbar.jsp" />
    <style>
        body {
            background-color: #0b3d91;
            color: white;
            text-align: center;
            font-family: Arial, sans-serif;
        }
        .card {
            width: 80px;
            height: 120px;
            display: inline-block;
            margin: 5px;
            border-radius: 8px;
            animation: flip 0.5s;
        }
        .card img {
            width: 100%;
            height: 100%;
            border-radius: 8px;
            object-fit: cover;
            border: 1px solid white;
        }
        button {
            padding: 10px 20px;
            margin: 10px;
            font-size: 18px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }
        .btn-green { background-color: #28a745; color: white; }
        .btn-yellow { background-color: #ffc107; color: black; }
        .btn-blue { background-color: #007bff; color: white; }
    </style>
</head>
<body>

    <h1>Benvenuto, <%= session.getAttribute("username") %></h1>
    <h2>Gioco del Blackjack</h2>

<div class="container">

<div>
    <h4>Carte Rimanenti nel Mazzo</h4>
    <div id="mazzoRimanente" style="height: 130px;"></div>
</div>
<div>
    <h3>Carte del Banco</h3>
    <div id="bankHand"></div>

    <h3>Le tue Carte</h3>
    <div id="playerHand"></div>

    <h4>Punteggio Giocatore: <span id="punteggioGiocatore">0</span></h4>
    <h4>Punteggio Banco: <span id="punteggioBanco">0</span></h4>

    <div>
        <button class="btn-green" onclick="takeAction('hit')">Pescare</button>
        <button class="btn-yellow" onclick="takeAction('stand')">Restare</button>
        <button class="btn-blue" onclick="takeAction('nuova')">Nuova Partita</button>
    </div>
</div>
</div>


   

    <script>
        let immaginiCarte = {};

        function takeAction(action) {
            const xhr = new XMLHttpRequest();
            xhr.open("POST", "game", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    const response = JSON.parse(xhr.responseText);
                    immaginiCarte = response.immaginiCarte; // aggiorna mappa immagini

                    document.getElementById('playerHand').innerHTML = generateCardHTML(response.manoUser);
                    document.getElementById('bankHand').innerHTML = generateCardHTML(response.manoBot, response.bancoCoperte);
                    document.getElementById('punteggioGiocatore').innerText = response.punteggioGiocatore;
                    document.getElementById('punteggioBanco').innerText = response.punteggioBanco;
             
                    renderMazzo(response.carteNelMazzo);
                }
            };
            xhr.send("azione=" + action);
        }

        function generateCardHTML(cards, coverMode) {
            let html = '';
            cards.forEach(function(card, i) {
                let imgSrc;
                if (coverMode && i === 0) {
                    imgSrc = "img/carte/retro.png"; // carta coperta
                } else {
                    imgSrc = immaginiCarte[card.id]; // da mappa, fallback se manca
                }
                html += '<div class="card"><img src="' + imgSrc + '" alt="Carta"></div>';
            });
            return html;
        }

        function renderMazzo(quanteCarte) {
            const mazzoDiv = document.getElementById("mazzoRimanente");
            let html = '';
            for (let i = 0; i < quanteCarte; i++) {
                html += '<div class="card" style="position: absolute; left: ' + (i * 2) + 'px;"><img src="img/carte/retro.png" alt="Retro"></div>';
            }
            html += '<div style=\"position: absolute; left: 400px;\"><p>'+ quanteCarte +'</p></div>';
            mazzoDiv.style.position = "relative";
            mazzoDiv.style.width = (quanteCarte * 2 + 80) + "px";
            mazzoDiv.innerHTML = html;
        }
        
        // Stato iniziale
        window.onload = function() {
            takeAction('init');
        };
    </script>

</body>
</html>

