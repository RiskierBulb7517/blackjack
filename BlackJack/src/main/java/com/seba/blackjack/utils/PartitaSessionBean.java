package com.seba.blackjack.utils;

import java.io.Serializable;
import java.util.List;

import com.seba.blackjack.architecture.dao.DAOException;
import com.seba.blackjack.bc.model.Carta;

public class PartitaSessionBean implements Serializable {

	private static final long serialVersionUID = -8357964263009022179L;

	private List<Carta> carte;
	private List<Carta> manoBot;
	private List<Carta> manoUser;

	public PartitaSessionBean() throws DAOException {
		carte=DeckUtils.getDeck(2);
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
	
	public void drawCard(List<Carta> mano) {
		Carta drawn=carte.remove(carte.size()-1);
		mano.add(drawn);
	}
	
	
	
}
