package com.seba.blackjack.bc;

import java.sql.Connection;

import com.seba.blackjack.architecture.dao.DAOException;
import com.seba.blackjack.architecture.dao.UtenteDAO;
import com.seba.blackjack.architecture.dbaccess.DBAccess;
import com.seba.blackjack.bc.exception.UserNotFoundException;
import com.seba.blackjack.bc.model.Utente;
import com.seba.blackjack.security.Encrypt;

public class UtenteBC {

	private UtenteDAO udao;
	private Connection conn;
	
	private UtenteBC() {
		udao=UtenteDAO.getDao();
	}
	
	public static UtenteBC getUBC() {
		return new UtenteBC();
	}
	
	public void createUser(Utente utente) throws DAOException {
		
		try {
			conn=DBAccess.getConnection();
			
			utente.setPassword(Encrypt.converti(utente.getPassword()));
			udao.createUser(conn, utente);
			
			
			
		}finally {
			DBAccess.closeConnection(conn);
		}
		
	}
	
	public Utente getUser(String username) throws DAOException{
		
		try {
			conn=DBAccess.getConnection();
			return udao.getUserByUsername(conn, username);
			
		}finally {
			DBAccess.closeConnection(conn);
		}
		
	}
	
	public Utente[] getAllUser() throws DAOException{
		
		try {
			conn=DBAccess.getConnection();
			Utente[] utenti=udao.getAllUsers(conn);
			if(utenti==null) {
				return new Utente[0];
			}
			return utenti;
		}finally {
			DBAccess.closeConnection(conn);
		}
		
	}
	
	public void deleteUser(String username) throws DAOException{
		
		try {
			conn=DBAccess.getConnection();
			udao.deleteUser(conn, username);
			
		}finally {
			DBAccess.closeConnection(conn);
		}
		
	}

	public boolean checkPass(String username, String password) throws DAOException, UserNotFoundException{
		
		try {
			
			conn=DBAccess.getConnection();
			Utente utente=udao.getUserByUsername(conn, username);
			if(utente==null) {
				throw new UserNotFoundException("Utente non trovato.");
			}
			return Encrypt.verificaPassword(password, utente.getPassword());					
			
		}finally {
			DBAccess.closeConnection(conn);
		}
	}
	
	
	public boolean updatePassword(String username, String password, String passwordn) throws DAOException, UserNotFoundException{
		
		boolean check=checkPass(username, password);
		if(!check) {
			return false;
		}
		
		try {
			conn=DBAccess.getConnection();
			udao.updateUserPassword(conn, username, passwordn);
			return true;
		}finally {
			DBAccess.closeConnection(conn);
		}
		
	}
}
