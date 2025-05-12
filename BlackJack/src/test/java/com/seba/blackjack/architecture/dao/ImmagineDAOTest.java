package com.seba.blackjack.architecture.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.seba.blackjack.architecture.dao.ImmagineDAO;
import com.seba.blackjack.architecture.dbaccess.DBAccess;
import com.seba.blackjack.bc.model.Immagine;

class ImmagineDAOTest {

	private Connection conn;

	@Test
	@Order(1)
	void testGetByCartaID() {
		try {
			conn = DBAccess.getConnection();

			// ID di una carta reale nel tuo database
			long cartaId = 10L;

			Immagine immagine = ImmagineDAO.getDao().getByCartaID(conn, cartaId);

			assertNotNull(immagine, "L'immagine dovrebbe esistere");
			assertEquals(cartaId, immagine.getCartaid(), "Il cartaid deve corrispondere");
			assertNotNull(immagine.getUrl(), "L'URL non può essere null");
			assertFalse(immagine.getUrl().isEmpty(), "L'URL non può essere vuoto");

			System.out.println("ID: " + immagine.getId() + ", CartaID: " + immagine.getCartaid() + ", URL: " + immagine.getUrl());

			DBAccess.closeConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed: " + e.getMessage());
		}
	}
}

