package com.seba.blackjack.bc;

import java.sql.Connection;

import com.seba.blackjack.architecture.dao.CartaDAO;
import com.seba.blackjack.architecture.dao.DAOException;
import com.seba.blackjack.architecture.dbaccess.DBAccess;
import com.seba.blackjack.bc.model.Carta;

public class CartaBC {
	
	private CartaDAO cdao;
	private Connection conn;
	
	private CartaBC() {
		cdao=CartaDAO.getDao();
	}
	
	public static CartaBC getCBC() {
		return new CartaBC();
	}
	
	public Carta getCartaByID(long ID) throws DAOException{
		
		try {
			conn=DBAccess.getConnection();
			return cdao.getByID(conn, ID);
			
		}finally {
			DBAccess.closeConnection(conn);
		}
	}
	
	
	
	public Carta[] getAllCards() throws DAOException{
		
		try {
			conn=DBAccess.getConnection();
			Carta[] carte=cdao.getAll(conn);
			if(carte==null) {
				return new Carta[0];
			}
			return carte;
		}finally {
			DBAccess.closeConnection(conn);
		}
		
	}
	
	public Carta getCartaByValoreESeme(long valore, String seme) throws DAOException{
		
		try {
			conn=DBAccess.getConnection();
			return cdao.getByValoreESeme(conn, valore, seme);
			
		}finally {
			DBAccess.closeConnection(conn);
		}
	}

}
