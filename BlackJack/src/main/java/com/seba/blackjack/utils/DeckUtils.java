package com.seba.blackjack.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.seba.blackjack.architecture.dao.DAOException;
import com.seba.blackjack.bc.CartaBC;
import com.seba.blackjack.bc.model.Carta;

public class DeckUtils {
	
	private static List<Carta> deck;

	
	public static List<Carta> getDeck(int number) throws DAOException {
		if(deck == null || deck.size() != number * 52 ) {
			deck = new ArrayList<Carta>();
			for(int i = 0; i < number; i++) {
				deck.addAll(Arrays.asList(CartaBC.getCBC().getAllCards()));
			}
			Collections.shuffle(deck);
		}
		return deck;
	}

}
