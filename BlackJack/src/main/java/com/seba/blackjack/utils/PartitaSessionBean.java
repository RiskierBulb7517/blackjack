package com.seba.blackjack.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;

import com.seba.blackjack.architecture.dao.DAOException;
import com.seba.blackjack.bc.ImmagineBC;
import com.seba.blackjack.bc.PartitaPCBC;
import com.seba.blackjack.bc.model.Carta;
import com.seba.blackjack.bc.model.PartitaPC;

@Stateful
@SessionScoped
public class PartitaSessionBean implements Serializable {

    private static final long serialVersionUID = -8357964263009022179L;

    private List<Carta> carte;
    private List<Carta> manoBot;
    private List<Carta> manoUser;
    private boolean turnoGiocatore;
    private int punteggioGiocatore;
    private int punteggioBanco;
    private PartitaPC partita;
    private boolean attesaBot; // indica se il bot deve ancora pescare in modo progressivo
    private String messaggio; // messaggio per eventuali eventi (Black Jack, sballo, vittoria, ecc.)
    private boolean canHit;
    private boolean fineRound;

    // Inizializza la partita
    public void inizializzaPartita(String username) throws DAOException {
        carte = DeckUtils.getDeck(2);
        punteggioGiocatore = 0;
        punteggioBanco = 0;
        partita = new PartitaPC();
        partita.setPuntibanco(punteggioBanco);
        partita.setPuntiutente(punteggioGiocatore);
        partita.setStato("In corso");
        partita.setU_username(username);
        partita = PartitaPCBC.getPBC().createPartita(partita);
        messaggio = "";
        startRound();
    }

    // Inizia un round
    public void startRound() throws DAOException {
        if (carte.size() < 1) {
            partita.setStato("Finita");
            PartitaPCBC.getPBC().update(partita.getId(), punteggioBanco, punteggioGiocatore, "Finita");
            messaggio = "Partita finita!";
            return;
        }

        fineRound=false;
        manoUser = new ArrayList<>();
        manoBot = new ArrayList<>();
        turnoGiocatore = true;
        attesaBot = false;
        canHit=true;
        messaggio = "";

        drawCard(manoBot);
        drawCard(manoUser);
        drawCard(manoBot);
        drawCard(manoUser);

        blackJack();
    }

	public void blackJack() throws DAOException {
		boolean bjUser = checkBlackJack(manoUser);
        boolean bjBot = checkBlackJack(manoBot);

        if (bjUser || bjBot) {
            // Mostra il messaggio di blackjack
            if (bjUser && bjBot) {
                messaggio = "Black Jack per entrambi! Round Pareggiato.";
            } else if (bjUser) {
                punteggioGiocatore += 2;
                messaggio = "Black Jack! Hai vinto il round.";
            } else if (bjBot) {
                punteggioBanco += 2;
                messaggio = "Black Jack per il banco! Hai perso il round.";
            }
            salvaStato();
            concludiRound();
        }
	}

    // Il giocatore sceglie di pescare
    public void hit() throws DAOException {
    	if(!canHit) return;
        if (!turnoGiocatore) return;
        drawCard(manoUser);
        long punteggio = calcolaPunteggio(manoUser);
        if (punteggio > 21) {
            punteggioBanco++;
            messaggio = "Hai sballato!";
            salvaStato();
            concludiRound();
        } else if (punteggio == 21) {
            // Se si arriva a 21, il giocatore si ferma automaticamente
            canHit=false;
        }
    }

    // Il giocatore sceglie di fermarsi
    public void stand() {
        turnoGiocatore = false;
        attesaBot = true; // ora il bot inizia la sua giocata progressiva
        messaggio = "Il banco sta pescando...";
    }

    // Il bot pesca una carta in modalità step-by-step
    public void turnoBotStep() throws DAOException {
        if (turnoGiocatore || !attesaBot) {
            return;
        }
  
        if (calcolaPunteggio(manoBot) <= 16) {
            drawCard(manoBot);
            // Se dopo la pescata il punteggio è ancora ≤ 16, continua ad aspettare
        } else {
            attesaBot = false;
            eseguiCalcoloEsito();
        }
    }

	private void checkBotPoints() throws DAOException {
		if (calcolaPunteggio(manoBot) <= 16) {
		    attesaBot = true;
		} else {
		    attesaBot = false;
		    eseguiCalcoloEsito();
		}
	}

    // Calcola il punteggio finale e aggiorna i punteggi a fine turno bot
    private void eseguiCalcoloEsito() throws DAOException {
        long puntiBot = calcolaPunteggio(manoBot);
        long puntiUser = calcolaPunteggio(manoUser);
        // Riveliamo la carta coperta del banco quando si effettuano i calcoli
        // (Il client dovrebbe mostrare tutte le carte quando turnoGiocatore == false)
        if (puntiBot > 21 || puntiUser > puntiBot) {
            punteggioGiocatore++;
            messaggio = "Hai vinto il round!";
        } else if (puntiBot > puntiUser) {
            punteggioBanco++;
            messaggio = "Il banco ha vinto il round!";
        } else {
            messaggio = "Round Pareggiato!";
        }
        salvaStato();
        concludiRound();
    }

    // Termina il round e ne inizia uno nuovo
    private void concludiRound() throws DAOException {
    	fineRound = true;
        if (carte.size() < 1) {
            partita.setStato("Finita");
            PartitaPCBC.getPBC().update(partita.getId(), punteggioBanco, punteggioGiocatore, "Finita");
            messaggio = "Partita finita!";
            return;
        }
    }

    // Salva lo stato della partita nel DB
    private void salvaStato() throws DAOException {
        PartitaPCBC.getPBC().update(partita.getId(), punteggioBanco, punteggioGiocatore, partita.getStato());
        partita = PartitaPCBC.getPBC().getPartitaByID(partita.getId());
    }

    // Controlla se la mano ha fatto blackjack
    private boolean checkBlackJack(List<Carta> mano) {
        if (mano.size() == 2) {
            boolean hasAsso = mano.stream().anyMatch(c -> c.getValore() == 1);
            boolean hasFigura = mano.stream().anyMatch(c -> c.getValore() > 10);
            return hasAsso && hasFigura;
        }
        return false;
    }

    // Calcola il punteggio della mano (gestendo assi come 1 o 11)
    private long calcolaPunteggio(List<Carta> mano) {
        long somma = 0;
        long assi = 0;
        for (Carta c : mano) {
            long valore = c.getValore();
            if (valore > 10) valore = 10;
            if (valore == 1) assi++;
            somma += valore;
        }
        while (assi > 0 && somma + 10 <= 21) {
            somma += 10;
            assi--;
        }
        return somma;
    }

    // Pone una carta nella mano indicata e la rimuove dal mazzo
    private void drawCard(List<Carta> mano) throws DAOException {
        if (carte.isEmpty()) {
            concludiRound();
            return;
        }
        mano.add(carte.remove(0));
    }

    // GETTER
    public List<Carta> getManoBot() {
        return manoBot;
    }
    public List<Carta> getManoUser() {
        return manoUser;
    }
    public boolean isTurnoGiocatore() {
        return turnoGiocatore;
    }
    public int getPunteggioGiocatore() {
        return punteggioGiocatore;
    }
    public int getPunteggioBanco() {
        return punteggioBanco;
    }
    public boolean isPartitaFinita() {
        return "Finita".equals(partita.getStato());
    }
    public int getCarteRimanenti() {
        return (carte != null) ? carte.size() : 0;
    }
    public boolean isAttesaBot() {
        return attesaBot;
    }
    public String getMessaggio() {
        return messaggio;
    }

    // Metodo per la restituzione dello stato JSON
    public GameState getGameState() {
        Map<Long, String> immagini = new HashMap<>();
        List<Carta> tutteLeCarte = new ArrayList<>();
        tutteLeCarte.addAll(manoUser);
        tutteLeCarte.addAll(manoBot);

        for (Carta carta : tutteLeCarte) {
            try {
                String path = ImmagineBC.getUBC().getImmByCartaID(carta.getId()).getUrl();
                immagini.put(carta.getId(), path);
            } catch (DAOException e) {
                immagini.put(carta.getId(), "img/cards/placeholder.png");
            }
        }

        return new GameState(
            manoUser,
            manoBot,
            calcolaPunteggio(manoUser),
            punteggioGiocatore,
            punteggioBanco,
            turnoGiocatore,
            isPartitaFinita(),
            immagini,
            getCarteRimanenti(),
            attesaBot,
            messaggio,
            canHit,
            fineRound,
            checkBlackJack(manoBot)||checkBlackJack(manoUser)
        );
    }

    public boolean isFineRound() {
		return fineRound;
	}

	public void setFineRound(boolean fineRound) {
		this.fineRound = fineRound;
	}
	
	// Classe interna per il GameState (serializzabile in JSON)
    public static class GameState implements Serializable {
        private static final long serialVersionUID = 5097928478339206151L;
        private List<Carta> manoUser;
        private List<Carta> manoBot;
        private long punteggioManoGiocatore;
        private int punteggioGiocatore;
        private int punteggioBanco;
        private boolean turnoGiocatore;
        private boolean partitaFinita;
        private Map<Long, String> immaginiCarte; // ID carta -> URL immagine
        private int carteNelMazzo;
        private boolean attesaBot;
        private String messaggio;
        private boolean canHit;
        private boolean fineRound;
        private boolean blackJack;

        public GameState(List<Carta> manoUser, List<Carta> manoBot, long punteggioManoGiocatore, int punteggioGiocatore,
                         int punteggioBanco, boolean turnoGiocatore, boolean partitaFinita,
                         Map<Long, String> immaginiCarte, int carteNelMazzo, boolean attesaBot,
                         String messaggio, boolean canHit, boolean fineRound, boolean blackJack) {
            this.manoUser = manoUser;
            this.manoBot = manoBot;
            this.punteggioManoGiocatore=punteggioManoGiocatore;
            this.punteggioGiocatore = punteggioGiocatore;
            this.punteggioBanco = punteggioBanco;
            this.turnoGiocatore = turnoGiocatore;
            this.partitaFinita = partitaFinita;
            this.immaginiCarte = immaginiCarte;
            this.carteNelMazzo = carteNelMazzo;
            this.attesaBot = attesaBot;
            this.messaggio = messaggio;
            this.canHit = canHit;
            this.fineRound= fineRound;
            this.blackJack=blackJack;
        }
        // GETTER
        public List<Carta> getManoUser() { return manoUser; }
        public List<Carta> getManoBot() { return manoBot; }
        public long getPunteggioManoGiocatore() { return punteggioManoGiocatore; }
        public int getPunteggioGiocatore() { return punteggioGiocatore; }
        public int getPunteggioBanco() { return punteggioBanco; }
        public boolean isTurnoGiocatore() { return turnoGiocatore; }
        public boolean isPartitaFinita() { return partitaFinita; }
        public Map<Long, String> getImmaginiCarte() { return immaginiCarte; }
        public int getCarteNelMazzo() { return carteNelMazzo; }
        public boolean isAttesaBot() { return attesaBot; }
        public String getMessaggio() { return messaggio; }
        public boolean isCanHit() { return canHit; }
        
		public boolean isBlackJack() {
			return blackJack;
		}
		public boolean isFineRound() {
			return fineRound;
		}
		public void setFineRound(boolean fineRound) {
			this.fineRound = fineRound;
		}
    }
}
