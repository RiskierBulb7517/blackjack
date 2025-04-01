package test.com.seba.blackjack.architecture.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.seba.blackjack.architecture.dao.CartaDAO;
import com.seba.blackjack.architecture.dbaccess.DBAccess;
import com.seba.blackjack.bc.model.Carta;

class CartaDAOTest {

	private Connection conn;
	
	@Test
	@Order(1)
	void testGetAll() {
		
		try {
			conn=DBAccess.getConnection();
			Carta[] carte=CartaDAO.getDao().getAll(conn);
			assertNotNull(carte);
			assertTrue(carte.length==52);
			for(int i=0;i<carte.length;i++) {
				assertNotNull(carte[i]);
			}
			DBAccess.closeConnection(conn);		
		}catch(Exception e) {
			e.printStackTrace();
			fail("Failed: "+e.getMessage());
		}
	}
	
	@Test
	@Order(2)
	void testGetBySemeEValore() {
		
		try {
			//di default è' o 16 o 10;
			conn=DBAccess.getConnection();
			List<String> semi = new ArrayList<String>();
			semi.add("CUORI");
			semi.add("QUADRI");
			semi.add("FIORI");
			semi.add("PICCHE");
			for(String sesso:semi) {
				for(long i=1; i<14; i++) {
					Carta carta =CartaDAO.getDao().getByValoreESeme(conn, i, sesso);
					assertNotNull(carta);
					assertEquals(sesso, carta.getSeme());
					assertEquals(i, carta.getValore());
				}
			}
			
			DBAccess.closeConnection(conn);
		}catch(Exception e) {
			e.printStackTrace();
			fail("Failed: "+e.getMessage());
		}
	}

}
