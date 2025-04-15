package com.seba.blackjack.bc;

import java.sql.Connection;
import com.seba.blackjack.architecture.dao.DAOException;
import com.seba.blackjack.architecture.dao.PartitaPCDAO;
import com.seba.blackjack.architecture.dbaccess.DBAccess;
import com.seba.blackjack.bc.model.PartitaPC;

public class PartitaPCBC {
    
    private PartitaPCDAO pdao;
    private Connection conn;
    
    private PartitaPCBC() {
        pdao = PartitaPCDAO.getDAO();
    }
    
    public static PartitaPCBC getPBC() {
        return new PartitaPCBC();
    }
    
    public PartitaPC createPartita(PartitaPC partita) throws DAOException {
        try {
            conn = DBAccess.getConnection();
            return pdao.insertMatch(conn, partita);
            
        } finally {
            DBAccess.closeConnection(conn);
        }
    }
    
    public PartitaPC getPartitaByID(long ID) throws DAOException{
    	try {
    		conn=DBAccess.getConnection();
    		return pdao.getPartita(conn, ID);
    	} finally {
    		DBAccess.closeConnection(conn);
    	}
    }
    
    public PartitaPC[] getPartiteByUsername(String username) throws DAOException {
        
    	PartitaPC[] partite;
    	try {
            conn = DBAccess.getConnection();
            partite=pdao.getByUsername(conn, username);
            if(partite==null) {
            	return new PartitaPC[0];
            }
            return partite;
        } finally {
            DBAccess.closeConnection(conn);
        }
    }
    
    public PartitaPC[] getAllPartite() throws DAOException {
        try {
            conn = DBAccess.getConnection();
            PartitaPC[] partite = pdao.getAllMatches(conn);
            return partite != null ? partite : new PartitaPC[0];
        } finally {
            DBAccess.closeConnection(conn);
        }
    }
    
    public void deleteMatchByID(long ID) throws DAOException {
        try {
            conn = DBAccess.getConnection();
            pdao.deleteMatchByID(conn, ID);
        } finally {
            DBAccess.closeConnection(conn);
        }
    }
    
    public void update(long PartitaID, long pbanco, long putente, String stato) throws DAOException{
    	
    	try {
    		conn=DBAccess.getConnection();
    		pdao.update(conn, PartitaID, pbanco, putente, stato);
    		
    	}finally {
    		DBAccess.closeConnection(conn);
    	}
    }
    
    
    public int[] getPunteggioPartita(long ID) throws DAOException {
        try {
            conn = DBAccess.getConnection();
            return pdao.getPunteggio(conn, ID);
        } finally {
            DBAccess.closeConnection(conn);
        }
    }
}
