package com.seba.blackjack.architecture.dao;

public interface DAOConstants {
	
	String SELECT_USER="SELECT * FROM UTENTE WHERE Username = ? ";
	String INSERT_USER="INSERT INTO UTENTE VALUES (?, ?, ?)";
	String SELECT_USER_MATCHES="SELECT * FROM PARTITAPC WHERE U_Username = ?";
	String SELECT_CARDS="SELECT * FROM CARTA";
	String SELECT_IMAGECARD="SELECT * FROM IMMAGINE WHERE CARTA_ID = ?";
	String SELECT_CARDS_BY_VALUE_AND_SEED="SELECT * FROM CARTA WHERE Valore = ? AND Seme = ?";
	String SELECT_CARD_BY_ID="SELECT * FROM CARTA WHERE ID= ?";
	String INSERT_MATCH="INSERT INTO PARTITAPC(U_Username,Status,Puntibanco,Puntiutente) VALUES (?,?,?,?)";
	
}
