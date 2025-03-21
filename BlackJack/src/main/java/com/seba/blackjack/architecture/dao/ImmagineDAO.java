package com.seba.blackjack.architecture.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.seba.blackjack.bc.model.Immagine;

public class ImmagineDAO implements DAOConstants{

	private static ImmagineDAO dao;
	
	private ImmagineDAO() {
		
	}
	
	public static ImmagineDAO getDao() {
		if(dao==null) {
			dao= new ImmagineDAO();
		}
		return dao;
	}
	
public Immagine getByCartaID(Connection conn, long ID) throws DAOException{
		
		Immagine immagine=null;
		PreparedStatement prst;
		
		try {
			prst = conn.prepareStatement(SELECT_IMAGECARD);
			prst.setLong(1, ID);
			
			ResultSet rs=prst.executeQuery();
			
			if(rs.next()) {
				immagine=new Immagine();
				immagine.setId(rs.getLong(1));
				immagine.setCartaid(rs.getLong(2));
				immagine.setUrl(rs.getString(3));
			}
			conn.commit();
		}catch(SQLException e) {
			throw new DAOException(e);
		}
		return immagine;
		
	}
	
}
