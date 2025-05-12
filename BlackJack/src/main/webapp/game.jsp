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
            height: 100vh;
            
        }
        html, body {
		    margin: 0;
		    padding: 0;
		    height: 100vh;
		    overflow: hidden; /* blocca lo scroll verticale */
		    display: flex;
		    flex-direction: column;
		}
		
		.container {
		    flex: 1;
		    display: flex;
		    flex-direction: column;
		    justify-content: space-between;
		    overflow: hidden;
		}
		
		.game-container {
		    flex: 1;
		    overflow: auto; /* se serve lo scroll interno, metti qui */
		}

        .card {
            width: 90px;
            height: 130px;
            display: inline-block;
            margin: 10px;
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
            padding: 8px 16px;
            margin: 8px;
            font-size: 16px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            outline: none;
            box-shadow: none;
            transition: background-color 0.2s, transform 0.1s;
        }

        button:focus,
        button:hover {
            outline: none;
            box-shadow: none;
            transform: scale(1.03);
        }

        .btn-green { background-color: #28a745; color: white; }
        .btn-yellow { background-color: #ffc107; color: black; }
        .btn-blue { background-color: #007bff; color: white; }

        /* New layout styles */
        .game-container {
		    display: flex;
		    justify-content: center; 
		    align-items: center;
		    margin-top: 20px;
		    margin-bottom: 20px;
		    position: relative; 
        }

        .cards-section {
            text-align: center;
        }

        .cards-section h3, .cards-section h4 {
            margin-bottom: 10px;
        }

        #mazzoRimanente {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-right: 30px;
        }

        #playerHand, #bankHand {
            display: flex;
            justify-content: center;
            margin-bottom: 20px;
        }

        .score-section {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }

        /* Repositioning elements */
        .game-container {
            flex-direction: row;
        }

        .hand-container {
            display: flex;
            flex-direction: column;
		    align-items: flex-start;
        }


        #bankHand {
            margin-bottom: 10px;
        }

        #playerHand {
            margin-top: 10px;
        }

        /* New style for remaining cards */
		.mazzo-container {
		    position: relative;
		    display: flex;
		    align-items: center;
		    justify-content: center;
		    width: 250px;
		}
		
		.mazzo-container .card {
		    position: relative;
		}
		
		.mazzo-container .card-number {
		    position: absolute;
		    top: -100px; /* Posiziona il numero sopra la carta */
		    left: 50%; /* Posiziona al centro orizzontalmente */
		    transform: translateX(-50%); /* Centra il numero rispetto alla carta */
		    font-size: 24px;
		    font-weight: bold;
		    color: white;
		}
		#messaggio {
		    min-height: 1.5em; /* oppure un'altezza fissa in px, tipo 40px */
		    margin: 0;
		    transition: opacity 0.3s ease;
		}
    </style>
</head>
<body>
    

       <div class="container mt-4">
       <h2>Gioco del Blackjack</h2>
		
        <div class="game-container shadow-lg">
            <!-- Mazzo Rimanente -->
			<div id="mazzoRimanente" class="mazzo-container">
			    <div class="card">
			        <img src="img/cards/back_card.png" alt="Retro">
			        <div class="card-number" id="mazzoCarteRimanenti">0</div> 
			    </div>
			</div>
            <div class="container">
                        <!-- Sezione Banco -->
			            <div class="hand-container">
			                <h4>Punteggio Banco: <span id="punteggioBanco">0</span></h3>
			                <div id="bankHand"></div>
			            </div>
			
			            <!-- Sezione Giocatore -->
			            <div class="hand-container">
			                <h4>Punteggio Giocatore: <span id="punteggioGiocatore">0</span></h3>
			                <div id="playerHand"></div>
			            </div>
            </div>

        </div>

        <div class="score-section">
            <h2 id="messaggio"></h2>
            <div>
                
                <button class="btn-green" onclick="takeAction('hit')" id="pesca">Pescare</button>
                <button class="btn-yellow" onclick="takeAction('stand')" id="resta">Restare</button>
                <button class="btn-blue" onclick="takeAction('nuova')">Nuova Partita</button>
            </div>
        </div>

    </div>


    <script>
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
                    document.getElementById('resta').disabled=response.blackJack;
                    document.getElementById('messaggio').innerText = "";
                    
                    
                    // Aggiorna il mazzo rimanente
                    renderMazzo(response.carteNelMazzo);
                    // Visualizza eventuale messaggio (es. "Hai sballato!", "Black Jack!", ecc.)
                    if(response.partitaFinita){
    	                Swal.fire({
    	                    icon: 'info',
    	                    title: 'Partita finita!',
    	                    html: `
    	                        <p><strong>`+response.messaggio+`</strong></p>
    	                        <p><strong>Carte del banco:</strong></p>`+
    	                        generateCardHTML(response.manoBot, false)+
    	                        `<p><strong>Carte del giocatore:</strong></p>`+
    	                        generateCardHTML(response.manoUser, false)+
    	                       ` <p><strong>Punteggio totale banco:`+response.punteggioBanco+ `</strong></p>`+
    	                       ` <p><strong>Punteggio totale giocatore:`+ response.punteggioGiocatore+ `</strong></p>`,
    	                    confirmButtonText: "Torna all'account"
    	                }).then((result) => {
    	                    // Fa partire un nuovo round solo quando l’utente clicca "Avanti"
    	                    if(result.isConfirmed){
    	                    	window.location.href = "account.jsp";	
    	                    } 
    	                });
                    }
                    if (response.blackJack&&!response.partitaFinita) {
                    	   setTimeout(function() {

                           	console.log("qui?")
                    		    Swal.fire({
                    		        icon: 'info',
                    		        title: 'Black Jack!',
                    		        html:`
            	                        <p><strong>`+response.messaggio+`</strong></p>
            	                        <p><strong>Carte del banco:</strong></p>`+
            	                        generateCardHTML(response.manoBot, false)+
            	                        `<p><strong>Carte del giocatore:</strong></p>`+
            	                        generateCardHTML(response.manoUser, false)+
            	                       ` <p><strong>Punteggio totale banco:`+response.punteggioBanco+ `</strong></p>`+
            	                       ` <p><strong>Punteggio totale giocatore:`+ response.punteggioGiocatore+ `</strong></p>`,
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

	                    html:`
	                        <p><strong>`+response.messaggio+`</strong></p>
	                        <p><strong>Carte del banco:</strong></p>`+
	                        generateCardHTML(response.manoBot, false)+
	                        `<p><strong>Carte del giocatore:</strong></p>`+
	                        generateCardHTML(response.manoUser, false)+
	                       ` <p><strong>Punteggio totale banco:`+response.punteggioBanco+ `</strong></p>`+
	                       ` <p><strong>Punteggio totale giocatore:`+ response.punteggioGiocatore+ `</strong></p>`,
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



        // Renderizza il mazzo rimanente (con un'immagine fissa e il numero di carte)
        function renderMazzo(quanteCarte) {
            const mazzoDiv = document.getElementById("mazzoCarteRimanenti");
            mazzoDiv.style.position = "relative";
            mazzoDiv.innerHTML = quanteCarte;
        }

        // Stato iniziale
        window.onload = function() {
            takeAction('init');
        };
    </script>

</body>
</html>

