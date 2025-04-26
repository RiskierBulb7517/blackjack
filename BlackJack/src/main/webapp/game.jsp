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
    <title>Blackjack</title>
    <%@ include file="cdn.html" %>
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
        <h2 id="messaggio"></h2>
    </div>

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
                <button class="btn-green" onclick="takeAction('hit')" id="pesca">Pescare</button>
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
                    // Aggiorna la mappa delle immagini
                    immaginiCarte = response.immaginiCarte;

                    // Aggiorna le mani
                    document.getElementById('playerHand').innerHTML = generateCardHTML(response.manoUser, false);
                    // Se il turno del giocatore è terminato, mostra tutte le carte del banco scoperte (la carta coperta viene rivelata)
                    document.getElementById('bankHand').innerHTML = generateCardHTML(response.manoBot, true);
                    
                    // Aggiorna punteggi
                    document.getElementById('punteggioGiocatore').innerText = response.punteggioGiocatore;
                    document.getElementById('punteggioBanco').innerText = response.punteggioBanco;
                    
                    document.getElementById('pesca').disabled=response.punteggioManoGiocatore==21||
                    						response.carteNelMazzo==0||response.blackJack;
                    
                    
                    
                    // Aggiorna il mazzo rimanente
                    renderMazzo(response.carteNelMazzo);
                    // Visualizza eventuale messaggio (es. "Hai sballato!", "Black Jack!", ecc.)
                    if(response.partitaFinita){
    	                Swal.fire({
    	                    icon: 'info',
    	                    title: 'Partita finita!',
    	                    html: "<p>"+response.messaggio+"</p>"+"<br><p>Punteggio banco: </p>"+response.punteggioBanco+
    	                    	 "<br><br><p>Carte del banco: </p><br>"+
    	                          generateCardHTML(response.manoBot, false)+
    	                          "<br>"+
    	                          "<p>Carte del giocatore: </p><br>"+"<br><p>Punteggio giocatore: </p>"+response.punteggioGiocatore+
    	                          generateCardHTML(response.manoUser, false),
    	                    confirmButtonText: "Torna all'account"
    	                }).then((result) => {
    	                    // Fa partire un nuovo round solo quando l’utente clicca "Avanti"
    	                    if(result.isConfirmed){
    	                    	window.location.href = "account.jsp";	
    	                    } 
    	                });
                    }
                    
                    
                       if (response.blackJack) {
                    	   setTimeout(function() {

                           	console.log("qui?")
                    		    Swal.fire({
                    		        icon: 'info',
                    		        title: 'Black Jack!',
                    		        html: "<p>" + response.messaggio + "</p><br><p>Punteggio banco: </p>" + response.punteggioBanco +
                    		              "<br><br><p>Carte del banco: </p><br>" +
                    		              generateCardHTML(response.manoBot, false) +
                    		              "<br>" +
                    		              "<p>Carte del giocatore: </p><br><br><p>Punteggio giocatore: </p>" + response.punteggioGiocatore +
                    		              generateCardHTML(response.manoUser, false),
                    		        confirmButtonText: 'Continua'
                    		    }).then(() => {
                    		        takeAction('round');
                    		    });
                    		}, 3000);

	               
	            } else { 
                    document.getElementById('messaggio').innerText = response.messaggio;}

	            if (response.fineRound&&!response.blackJack&& !response.partitaFinita) {
	                Swal.fire({
	                    title: 'Risultato',

	                    html: "<p>"+response.messaggio+"</p>"+"<br><p>Punteggio banco: </p>"+response.punteggioBanco+"<br><br><p>Carte del banco: </p><br>"+
                        generateCardHTML(response.manoBot, false)+
                        "<br>"+"<br><p>Punteggio giocatore: </p>"+response.punteggioGiocatore+
                        "<p>Carte del giocatore: </p><br>"+
                        generateCardHTML(response.manoUser, false),
	                    icon: response.vittoriaGiocatore === true ? 'success' : 
	                          response.vittoriaGiocatore === false ? 'error' : 'info',
	                    confirmButtonText: 'Avanti'
	                }).then(() => {
	                    // Fa partire un nuovo round solo quando l’utente clicca "Avanti"
	                    takeAction('round');
	                });
	            }

                    
                    // Se il turno è del bot, e c'è attesa per una nuova carta, richiede progressivamente 'botPlay'
                    if (!response.turnoGiocatore && response.attesaBot) {
                        setTimeout(() => takeAction('botPlay'), 1500);
                    }  
                }
            };
            xhr.send("azione=" + action);
        }

        // Genera l'HTML per mostrare le carte; se coverMode è true, la prima carta verrà mostrata coperta
        function generateCardHTML(cards, coverMode) {
            let html = '';
            cards.forEach(function(card, i) {
                let imgSrc;
                if (coverMode && i === 0) {
                    imgSrc = "img/cards/back_card.png"; // immagine della carta coperta
                } else {
                    imgSrc = immaginiCarte[card.id];
                }
                html += '<div class="card"><img src="' + imgSrc + '" alt="Carta"></div>';
            });
            return html;
        }

        // Renderizza il mazzo rimanente (con un'immagine fissa e il numero di carte)
        function renderMazzo(quanteCarte) {
            const mazzoDiv = document.getElementById("mazzoRimanente");
            let html = '<div class="card" style="position: absolute; left: 0px;"><img src="img/cards/back_card.png" alt="Retro"></div>';
            html += '<div style="position: absolute; left: 100px; top: 40px;"><p style="font-size: 20px;">' + quanteCarte + '</p></div>';
            mazzoDiv.style.position = "relative";
            mazzoDiv.innerHTML = html;
        }

        // Stato iniziale
        window.onload = function() {
            takeAction('init');
        };
    </script>

</body>
</html>

