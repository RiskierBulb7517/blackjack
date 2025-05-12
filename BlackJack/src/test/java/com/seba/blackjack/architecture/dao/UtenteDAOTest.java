package com.seba.blackjack.architecture.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.seba.blackjack.architecture.dao.UtenteDAO;
import com.seba.blackjack.architecture.dbaccess.DBAccess;
import com.seba.blackjack.bc.model.Utente;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UtenteDAOTest {
	
	private static Utente user;
	private Connection conn;
	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		user = new Utente();
		user.setUsername("sam");
		user.setEmail("sam@mail.com");
		user.setPassword("criptedhash");
	}

	@Order(1)
	@Test
	void testCreateUserAndGetByUsername() {
		try {
			conn = DBAccess.getConnection();
			UtenteDAO.getDao().createUser(conn, user);
			user = UtenteDAO.getDao().getUserByUsername(conn, user.getUsername());
			assertNotNull(user);
			assertEquals("sam", user.getUsername());
			DBAccess.closeConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed: "+e.getMessage());
		}
	}
	
	@Test
	@Order(2)
	void testGetAllUsers() {
		try {
			conn = DBAccess.getConnection();
			Utente[] utenti=UtenteDAO.getDao().getAllUsers(conn);
			assertNotNull(utenti);
			assertTrue(utenti.length>=1);
			for(int i=0; i<utenti.length;i++) {
				assertNotNull(utenti[i]);
			}
			DBAccess.closeConnection(conn);
			
		}catch(Exception e) {
			e.printStackTrace();
			fail("Failed: "+e.getMessage());
		}
	}
	
	@Order(3)
	@Test
	void testDeleteUser() {
		try {
			conn = DBAccess.getConnection();
			UtenteDAO.getDao().deleteUser(conn, user.getUsername());
			user = UtenteDAO.getDao().getUserByUsername(conn, user.getUsername());
			assertNull(user);
			DBAccess.closeConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed: "+e.getMessage());
		}
	}

	//update password test da fare --- trallallero trallalla
	
}
