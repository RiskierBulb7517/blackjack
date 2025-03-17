package com.seba.blackjack.architecture.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.seba.blackjack.bc.model.PartitaPC;

public class PartitaPCDAO implements DAOConstants {


	public static PartitaPCDAO dao;
	
	private PartitaPCDAO() {
		
	}
	
	public static PartitaPCDAO getDAO() {
		if(dao==null) {
			dao= new PartitaPCDAO();
		}
		return dao;
	}
	
	
	//MetodoInserimentoPartita
	
	public void insertMatch(Connection conn, PartitaPC partita) throws DAOException {
	    PreparedStatement prst = null;

	    try {
	        prst = conn.prepareStatement(INSERT_MATCH);
	        prst.setString(1, partita.getU_username()); 
	        prst.setString(2, partita.getStato());   
	        prst.setLong(3, partita.getPuntibanco());  
	        prst.setLong(4, partita.getPuntiutente());

	        prst.executeUpdate();
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

	
	
	
	
	//GetPartiteInBaseAll'Utente
	
	public PartitaPC[] getByUsername(Connection conn, String username) throws DAOException {
	    PartitaPC[] partite = null;
	    PreparedStatement prst = null;
	    ResultSet rs = null;

	    try {
	        prst = conn.prepareStatement(SELECT_USER_MATCHES, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        prst.setString(1, username);
	        rs = prst.executeQuery();

	        rs.last(); 
	        int size = rs.getRow();
	        rs.beforeFirst(); 

	        partite = new PartitaPC[size];

	        for (int i = 0; rs.next(); i++) {
	            PartitaPC partita = new PartitaPC();
	            partita.setId(rs.getLong(1));
	            partita.setU_username(rs.getString(2));
	            partita.setStato(rs.getString(3));
	            partita.setPuntibanco(rs.getLong(4));
	            partita.setPuntiutente(rs.getLong(5));
	            partite[i] = partita;
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (prst != null) prst.close();
	        } catch (SQLException e) {
	            throw new DAOException(e);
	        }
	    }

	    return partite;
	}
	
	public void updatePunteggio(Connection conn, long partitaID, int puntiUtente, int puntiBanco) throws DAOException {
	    PreparedStatement prst = null;

	    try {
	        prst = conn.prepareStatement(UPDATE_POINTS);
	        prst.setInt(1, puntiUtente);
	        prst.setInt(2, puntiBanco);
	        prst.setLong(3, partitaID);

	        prst.executeUpdate();
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        try {
	            if (prst != null) prst.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public int[] getPunteggio(Connection conn, long partitaID) throws DAOException {
	    PreparedStatement prst = null;
	    ResultSet rs = null;
	    int[] punteggi = new int[2]; // [0] = PuntiUtente, [1] = PuntiBanco

	    try {
	        prst = conn.prepareStatement(SELECT_POINTS);
	        prst.setLong(1, partitaID);
	        
	        rs = prst.executeQuery();
	        
	        if (rs.next()) {
	            punteggi[0] = rs.getInt("Puntiutente");
	            punteggi[1] = rs.getInt("Puntibanco");
	        }
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
	    return punteggi;
	}
	
	
	//delete match by the id
	public void deleteMatchByID(Connection conn, long partitaID) throws DAOException {
	    PreparedStatement prst = null;

	    try {
	        prst = conn.prepareStatement(DELETE_MATCH);
	        prst.setLong(1, partitaID);

	        prst.executeUpdate();
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        try {
	            if (prst != null) prst.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	public int countMatchesByUser(Connection conn, String username) throws DAOException {
	    PreparedStatement prst = null;
	    ResultSet rs = null;
	    int count = 0;

	    try {
	        prst = conn.prepareStatement(COUNT_MATCHES_BY_USER);
	        prst.setString(1, username);
	        rs = prst.executeQuery();
	        if (rs.next()) {
	            count = rs.getInt(1);
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (prst != null) prst.close();
	        } catch (SQLException e) {
	            throw new DAOException(e);
	        }
	    }
	    return count;
	}

	public PartitaPC getLastMatchByUser(Connection conn, String username) throws DAOException {
	    PreparedStatement prst = null;
	    ResultSet rs = null;
	    PartitaPC partita = null;

	    try {
	        prst = conn.prepareStatement(SELECT_LAST_MATCH_BY_USER);
	        prst.setString(1, username);
	        rs = prst.executeQuery();

	        if (rs.next()) {
	            partita = new PartitaPC();
	            partita.setId(rs.getLong(1));
	            partita.setU_username(rs.getString(2));
	            partita.setStato(rs.getString(3));
	            partita.setPuntibanco(rs.getLong(4));
	            partita.setPuntiutente(rs.getLong(5));
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (prst != null) prst.close();
	        } catch (SQLException e) {
	            throw new DAOException(e);
	        }
	    }
	    return partita;
	}

	public void deleteAllMatchesByUser(Connection conn, String username) throws DAOException {
	    PreparedStatement prst = null;

	    try {
	        prst = conn.prepareStatement(DELETE_ALL_MATCHES_BY_USER);
	        prst.setString(1, username);
	        prst.executeUpdate();
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

	public String[] getTopPlayers(Connection conn, int limit) throws DAOException {
	    String[] topPlayers = new String[limit];
	    PreparedStatement prst = null;
	    ResultSet rs = null;

	    try {
	        prst = conn.prepareStatement(SELECT_TOP_PLAYERS);
	        prst.setInt(1, limit);
	        rs = prst.executeQuery();

	        int i = 0;
	        while (rs.next() && i < limit) {
	            topPlayers[i] = rs.getString("U_Username") + " - Punti: " + rs.getInt("TotalPoints");
	            i++;
	        }
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
	    return topPlayers;
	}

}
