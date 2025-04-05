package com.seba.blackjack.bc.controller;


import com.google.gson.Gson;
import com.seba.blackjack.utils.PartitaSessionBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/game")
public class GameServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        PartitaSessionBean partita = (PartitaSessionBean) session.getAttribute("partitaBean");

        if (partita == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Partita non trovata.\"}");
            return;
        }

        String azione = request.getParameter("azione");
        try {
            switch (azione) {
                case "hit":
                    partita.hit();
                    break;
                case "stand":
                    partita.stand();
                    break;
                case "nuova":
                    String username = (String) session.getAttribute("username");
                    partita.inizializzaPartita(username);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
            return;
        }

        com.seba.blackjack.utils.PartitaSessionBean.GameState gameState = partita.getGameState();
        String json = gson.toJson(gameState);
        response.getWriter().write(json);
    }
}
