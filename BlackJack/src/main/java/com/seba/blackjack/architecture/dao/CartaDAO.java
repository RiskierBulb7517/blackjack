package com.seba.blackjack.architecture.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.seba.blackjack.bc.model.Carta;

public class CartaDAO implements DAOConstants{
	
	private static CartaDAO dao;
	
	
	private CartaDAO() {
		
	}
	
	public static CartaDAO getDao() {
		if(dao==null) {
			dao= new CartaDAO();
		}
		return dao;
	}
	
	public Carta[] getAll(Connection conn) throws DAOException {
		Carta[] carte = null;
		try {
			
			Statement stt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stt.executeQuery(SELECT_CARDS);

			rs.last();
			carte = new Carta[rs.getRow()];
			rs.beforeFirst();
			
			for(int i=0; rs.next(); i++) {
				Carta carta=new Carta();
				carta.setId(rs.getLong(1));
				carta.setValore(rs.getLong(2));
				carta.setSeme(rs.getString(3));
				carte[i]=carta;
			}
			
			
		}catch (SQLException e) {
			throw new DAOException(e);
		}
		return carte;
	}
	
	
	public Carta getByID(Connection conn, long ID) throws DAOException{
		
		Carta carta=null;
		PreparedStatement prst;
		
		try {
			prst = conn.prepareStatement(SELECT_CARD_BY_ID);
			prst.setLong(1, ID);
			
			ResultSet rs=prst.executeQuery();
			
			if(rs.next()) {
				carta=new Carta();
				carta.setId(rs.getLong(1));
				carta.setValore(rs.getLong(2));
				carta.setSeme(rs.getString(3));
			}
		}catch(SQLException e) {
			throw new DAOException(e);
		}
		return carta;
		
	}
	
	//metodo getbyvaloreeseme
	/*
	 * Carta carta=null;
		PreparedStatement prst;
		
		try {
			prst = conn.prepareStatement(SELECT_CARD_BY_ID);
			prst.setLong(1, ID);
			
			for(int i=0; rs.next(); i++) {
				Carta carta=new Carta();
				carta.setId(rs.getLong(1));
				carta.setValore(rs.getLong(2));
				carta.setSeme(rs.getString(3));
				carte[i]=carta;
			}
			
	 * 
	 * 
	 * 
	 * 
	 * */
}
