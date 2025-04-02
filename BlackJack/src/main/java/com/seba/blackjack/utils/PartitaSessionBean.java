package com.seba.blackjack.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;

import com.seba.blackjack.architecture.dao.DAOException;
import com.seba.blackjack.bc.model.Carta;
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

	public PartitaSessionBean() throws DAOException {
		carte=DeckUtils.getDeck(2);
	    punteggioGiocatore = 0;
	    punteggioBanco = 0;
		startRound();
	}

	private void startRound() throws DAOException {
		if(carte.size() < 4) return;
		manoUser= new ArrayList<Carta>();
		manoBot = new ArrayList<Carta>();
		turnoGiocatore = true;
		manoBot.add(drawCard());
		manoUser.add(drawCard());
		manoBot.add(drawCard());
		manoUser.add(drawCard());
	}
	
	
	
	public List<Carta> getManoBot() {
		return manoBot;
	}

	public void setManoBot(List<Carta> manoBot) {
		this.manoBot = manoBot;
	}

	public List<Carta> getManoUser() {
		return manoUser;
	}

	public void setManoUser(List<Carta> manoUser) {
		this.manoUser = manoUser;
	}

	public List<Carta> getCarte() {
		return carte;
	}
	
	public Carta drawCard() {
		if(carte.isEmpty()) return null;
		return carte.remove(0);
	}
	
	
	
}
