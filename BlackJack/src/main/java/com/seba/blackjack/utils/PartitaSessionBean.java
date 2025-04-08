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
	private boolean attesaBot; 

	
	//Inizializza la partita
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

	//Inizia un round
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

		boolean bjUser = checkBlackJack(manoUser);
		boolean bjBot = checkBlackJack(manoBot);

		if (bjUser && bjBot) {
			concludiRound();
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

	// Il giocatore sceglie di pescare
	public void hit() throws DAOException {
		if (!turnoGiocatore) return;
		manoUser.add(drawCard());
		long punteggio = calcolaPunteggio(manoUser);

		if (punteggio > 21) {
			punteggioBanco++;
			salvaStato();
			concludiRound();
		} else if (punteggio == 21) {
			stand();
		}
	}

	//Il giocatore sceglie di fermarsi
	public void stand() {
		turnoGiocatore = false;
		attesaBot=true;
		
		
	}
	
	public void turnoBot() throws DAOException{
		
		if(turnoGiocatore||!attesaBot) {
			return;
		}
		
		while (calcolaPunteggio(manoBot) <= 16) {
			manoBot.add(drawCard());
		}

		long puntiBot = calcolaPunteggio(manoBot);
		long puntiUser = calcolaPunteggio(manoUser);

		if (puntiBot > 21 || puntiUser > puntiBot) {
			System.err.println("Punti bot: "+puntiBot);
			punteggioGiocatore++;
		} else if (puntiBot > puntiUser) {
			punteggioBanco++;
		}
		salvaStato();
		concludiRound();
		
	}

	//Il round si conclude e ne inizia un'altro
	private void concludiRound() throws DAOException {
		if (carte.size() < 1) {
			partita.setStato("Finita");
			PartitaPCBC.getPBC().update(partita.getId(), punteggioBanco, punteggioGiocatore, "Finita");
			return;
		}
		startRound();
	}

	//Salva lo stato della partita
	private void salvaStato() throws DAOException {
		PartitaPCBC.getPBC().update(partita.getId(), punteggioBanco, punteggioGiocatore, partita.getStato());
		partita = PartitaPCBC.getPBC().getPartitaByID(partita.getId());
	}

	//COntrolla se la mano ha fatto blackjack
	private boolean checkBlackJack(List<Carta> mano) {
		if (mano.size() == 2) {
			boolean hasAsso = mano.stream().anyMatch(c -> c.getValore() == 1);
			boolean hasFigura = mano.stream().anyMatch(c -> c.getValore() > 10);
			return hasAsso && hasFigura;
		}
		return false;
	}

	//Calcola il punteggo della mano
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
		somma += assi;
		
		return somma;
	}

	private Carta drawCard() {
		if (carte.isEmpty()) return null;
		return carte.remove(0);
	}

	// Getter

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

	// Metodo per restituzione stato JSON
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
				// In caso di errore, puoi mettere una immagine placeholder
				immagini.put(carta.getId(), "img/carte/placeholder.png");
			}
		}

		return new GameState(
			manoUser,
			manoBot,
			punteggioGiocatore,
			punteggioBanco,
			turnoGiocatore,
			isPartitaFinita(),
			immagini,
			carte.size(),
			attesaBot
		);
	}

	


	public boolean isAttesaBot() {
		return attesaBot;
	}

	public void setAttesaBot(boolean attesaBot) {
		this.attesaBot = attesaBot;
	}




	public static class GameState  implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 5097928478339206151L;
		private List<Carta> manoUser;
		private List<Carta> manoBot;
		private int punteggioGiocatore;
		private int punteggioBanco;
		private boolean turnoGiocatore;
		private boolean partitaFinita;
		private Map<Long, String> immaginiCarte; // ID carta -> URL immagine
		private int carteNelMazzo;
		private boolean attesaBot;

		public GameState(List<Carta> manoUser, List<Carta> manoBot, int punteggioGiocatore,
				int punteggioBanco, boolean turnoGiocatore, boolean partitaFinita,
				Map<Long, String> immaginiCarte, int carteNelMazzo, boolean attesaBot) {
			this.manoUser = manoUser;
			this.manoBot = manoBot;
			this.punteggioGiocatore = punteggioGiocatore;
			this.punteggioBanco = punteggioBanco;
			this.turnoGiocatore = turnoGiocatore;
			this.partitaFinita = partitaFinita;
			this.immaginiCarte = immaginiCarte;
			this.carteNelMazzo = carteNelMazzo;
			this.attesaBot = attesaBot;
		}

		public List<Carta> getManoUser() {
			return manoUser;
		}

		public void setManoUser(List<Carta> manoUser) {
			this.manoUser = manoUser;
		}

		public List<Carta> getManoBot() {
			return manoBot;
		}

		public void setManoBot(List<Carta> manoBot) {
			this.manoBot = manoBot;
		}

		public int getPunteggioGiocatore() {
			return punteggioGiocatore;
		}

		public void setPunteggioGiocatore(int punteggioGiocatore) {
			this.punteggioGiocatore = punteggioGiocatore;
		}

		public int getPunteggioBanco() {
			return punteggioBanco;
		}

		public void setPunteggioBanco(int punteggioBanco) {
			this.punteggioBanco = punteggioBanco;
		}

		public boolean isTurnoGiocatore() {
			return turnoGiocatore;
		}

		public void setTurnoGiocatore(boolean turnoGiocatore) {
			this.turnoGiocatore = turnoGiocatore;
		}

		public boolean isPartitaFinita() {
			return partitaFinita;
		}

		public void setPartitaFinita(boolean partitaFinita) {
			this.partitaFinita = partitaFinita;
		}

		public Map<Long, String> getImmaginiCarte() {
			return immaginiCarte;
		}

		public void setImmaginiCarte(Map<Long, String> immaginiCarte) {
			this.immaginiCarte = immaginiCarte;
		}

		public int getCarteNelMazzo() {
			return carteNelMazzo;
		}

		public void setCarteNelMazzo(int carteNelMazzo) {
			this.carteNelMazzo = carteNelMazzo;
		}

		public boolean isAttesaBot() {
			return attesaBot;
		}

		public void setAttesaBot(boolean attesaBot) {
			this.attesaBot = attesaBot;
		}

		
	}

}

