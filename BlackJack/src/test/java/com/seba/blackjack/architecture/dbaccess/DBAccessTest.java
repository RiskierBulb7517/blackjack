package com.seba.blackjack.architecture.dbaccess;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

import com.seba.blackjack.architecture.dao.DAOException;
import com.seba.blackjack.architecture.dbaccess.DBAccess;

class DBAccessTest {

	Connection conn;

	@Test
	void testConnection() {
		try {
			conn = DBAccess.getConnection();
			assertNotNull(conn, "La connessione non deve essere null");
		} catch (DAOException e) {
			e.printStackTrace();
			fail("Errore nel tentativo di connessione: "+e.getMessage());
		} finally {
			try {
				DBAccess.closeConnection(conn);
			} catch (DAOException e) {
				e.printStackTrace();
				fail("Errore nel tentativo di connessione: "+e.getMessage());
			}
		}
	}

}
