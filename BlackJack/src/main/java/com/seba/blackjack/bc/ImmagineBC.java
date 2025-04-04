package com.seba.blackjack.bc;

import java.sql.Connection;

import com.seba.blackjack.architecture.dao.DAOException;
import com.seba.blackjack.architecture.dao.ImmagineDAO;
import com.seba.blackjack.architecture.dbaccess.DBAccess;
import com.seba.blackjack.bc.model.Immagine;

public class ImmagineBC {

	private ImmagineDAO idao;
	private Connection conn;
	
	private ImmagineBC() {
		idao=ImmagineDAO.getDao();
	}
	
	public static ImmagineBC getUBC() {
		return new ImmagineBC();
	}
	
public Immagine getImmByCartaID(long ID) throws DAOException{
		
		try {
			conn=DBAccess.getConnection();
			return idao.getByCartaID(conn, ID);
			
		}finally {
			DBAccess.closeConnection(conn);
		}
	}

	
}
