package com.seba.blackjack.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;

import com.seba.blackjack.architecture.dao.DAOException;
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
		startRound();
	}

	private void startRound() throws DAOException {
		if (carte.size() < 1) {
			partita.setStato("Finita");
			PartitaPCBC.getPBC().update(partita.getId(), punteggioBanco, punteggioGiocatore, "Finita");
			return;
		}

		manoUser = new ArrayList<>();
		manoBot = new ArrayList<>();
		turnoGiocatore = true;

		manoBot.add(drawCard()); 
		manoUser.add(drawCard()); 
		manoBot.add(drawCard()); 
		manoUser.add(drawCard()); 

		// Controllo Black Jack iniziale
		boolean bjUser = checkBlackJack(manoUser);
		boolean bjBot = checkBlackJack(manoBot);

		if (bjUser && bjBot) {
			concludiRound(); // pareggio
		} else if (bjUser) {
			punteggioGiocatore += 2;
			salvaStato();
			concludiRound();
		} else if (bjBot) {
			punteggioBanco += 2;
			salvaStato();
			concludiRound();
		}
	}

	public void hit() throws DAOException {
		if (!turnoGiocatore) return;
		manoUser.add(drawCard());
		long punteggio = calcolaPunteggio(manoUser);

		if (punteggio > 21) {
			punteggioBanco++;
			salvaStato();
			concludiRound();
		} else if (punteggio == 21) {
			stand(); // passa al banco
		}
	}

	public void stand() throws DAOException {
		turnoGiocatore = false;

		while (calcolaPunteggio(manoBot) <= 16) {
			manoBot.add(drawCard());
		}

		long puntiBot = calcolaPunteggio(manoBot);
		long puntiUser = calcolaPunteggio(manoUser);

		if (puntiBot > 21 || puntiUser > puntiBot) {
			punteggioGiocatore++;
		} else if (puntiBot > puntiUser) {
			punteggioBanco++;
		}
		salvaStato();
		concludiRound();
	}

	private void concludiRound() throws DAOException {
		if (carte.size() < 1) {
			partita.setStato("Finita");
			PartitaPCBC.getPBC().update(partita.getId(), punteggioBanco, punteggioGiocatore, "Finita");
			return;
		}
		startRound();
	}

	private void salvaStato() throws DAOException {
		PartitaPCBC.getPBC().update(partita.getId(), punteggioBanco, punteggioGiocatore, partita.getStato());
		partita = PartitaPCBC.getPBC().getPartitaByID(partita.getId());
	}

	private boolean checkBlackJack(List<Carta> mano) {
		if (mano.size() == 2) {
			boolean hasAsso = mano.stream().anyMatch(c -> c.getValore() == 1);
			boolean hasFigura = mano.stream().anyMatch(c -> c.getValore() > 10);
			return hasAsso && hasFigura;
		}
		return false;
	}

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

	private Carta drawCard() {
		if (carte.isEmpty()) return null;
		return carte.remove(0);
	}

	// Getter e Setter

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
}
