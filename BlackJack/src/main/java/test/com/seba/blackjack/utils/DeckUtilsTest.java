package test.com.seba.blackjack.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.seba.blackjack.architecture.dao.DAOException;
import com.seba.blackjack.bc.model.Carta;
import com.seba.blackjack.utils.DeckUtils;

class DeckUtilsTest {
   
    
 
    @Test
    void testGetDeckSize() throws DAOException {
        List<Carta> deck = DeckUtils.getDeck(2);
        assertEquals(104, deck.size(), "Il mazzo dovrebbe avere 104 carte");
    }
    
    @Test
    void testDeckNotNull() throws DAOException {
        List<Carta> deck = DeckUtils.getDeck(1);
        assertNotNull(deck, "Il mazzo non deve essere null");
        assertFalse(deck.isEmpty(), "Il mazzo non deve essere vuoto");
    }
    

}
