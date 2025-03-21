package com.seba.blackjack.architecture.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.seba.blackjack.bc.model.Utente;

public class UtenteDAO implements DAOConstants{

	//INSERIMENTO da fare in dos
	/* Impostare la query coi parametri come sempre, preparestatement.execute()
	 * conn.commit();
	 * 
	 * per non passare tutti i campi dell'utente gli passo l'oggetto Utente direttamente
	 * 
	 * */
	
	public static UtenteDAO dao;
	
	private UtenteDAO() {
		
	}
	
	public static UtenteDAO getDao() {
		if(dao==null) {
			dao= new UtenteDAO();
		}
		return dao;
	}
	

	public Utente getUserByUsername(Connection conn, String username) throws DAOException {
	    PreparedStatement prst = null;
	    ResultSet rs = null;
	    Utente utente = null;

	    try {
	        prst = conn.prepareStatement(SELECT_USER_BY_USERNAME);
	        prst.setString(1, username);

	        rs = prst.executeQuery();
	        conn.commit();
	        if (rs.next()) {
	            utente = new Utente();
	            utente.setUsername(rs.getString("Username"));
	            utente.setEmail(rs.getString("Email"));
	            utente.setPassword(rs.getString("Password"));
	        }
	        conn.commit();
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (prst != null) prst.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return utente;
	}

	public Utente[] getAllUsers(Connection conn) throws DAOException {
	    Utente[] utenti = null;
	    PreparedStatement prst = null;
	    ResultSet rs = null;

	    try {
	        prst = conn.prepareStatement(SELECT_ALL_USERS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        rs = prst.executeQuery();

	        rs.last();
	        utenti = new Utente[rs.getRow()];
	        rs.beforeFirst();

	        for (int i = 0; rs.next(); i++) {
	            Utente utente = new Utente();
	            utente.setUsername(rs.getString("Username"));
	            utente.setEmail(rs.getString("Email"));
	            utente.setPassword(rs.getString("Password"));
	            utenti[i] = utente;
	        }
	        conn.commit();
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (prst != null) prst.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return utenti;
	}

	public void deleteUser(Connection conn, String Username) throws DAOException {
	    PreparedStatement prst = null;

	    try {
	        prst = conn.prepareStatement(DELETE_USER);
	        prst.setString(1, Username);
	        prst.executeUpdate();
	        conn.commit();
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        try {
	            if (prst != null) prst.close();
	        } catch (SQLException e) {
	            throw new DAOException(e);
	        }
	    }
	}

	public void updateUserPassword(Connection conn, String Username, String newPassword) throws DAOException {
	    PreparedStatement prst = null;

	    try {
	        prst = conn.prepareStatement(UPDATE_USER_PASSWORD);
	        prst.setString(1, newPassword);
	        prst.setString(2, Username);
	        prst.executeUpdate();
	        conn.commit();
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        try {
	            if (prst != null) prst.close();
	        } catch (SQLException e) {
	            throw new DAOException(e);
	        }
	    }
	}
	
	public void createUser(Connection conn, Utente utente) throws DAOException{
		
		PreparedStatement prst = null;

	    try {
	        prst = conn.prepareStatement(INSERT_USER);
	        prst.setString(1, utente.getUsername());
	        prst.setString(2, utente.getEmail());
	        prst.setString(3, utente.getPassword());
	        prst.executeUpdate();
	        conn.commit();
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        try {
	            if (prst != null) prst.close();
	        } catch (SQLException e) {
	            throw new DAOException(e);
	        }
	    }
		
	}
}
