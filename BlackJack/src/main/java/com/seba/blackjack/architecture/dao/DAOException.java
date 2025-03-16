package com.seba.blackjack.architecture.dao;

import java.sql.SQLException;

public class DAOException extends SQLException{

	private static final long serialVersionUID = 2294003045012853190L;
	
	public DAOException(String message) {
		super(message);
	}

	public DAOException(SQLException e) {
		super(e);
	}

}
