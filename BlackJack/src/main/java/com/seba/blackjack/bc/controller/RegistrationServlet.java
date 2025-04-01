package com.seba.blackjack.bc.controller;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.seba.blackjack.bc.UtenteBC;
import com.seba.blackjack.bc.model.Utente;
import com.seba.blackjack.security.EscapeHTML;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/registra")
public class RegistrationServlet extends HttpServlet {
       
	private static final long serialVersionUID = 8860893746526124555L;
	private UtenteBC utenteBC;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		utenteBC=UtenteBC.getUBC();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String username=request.getParameter("username");
			String email=request.getParameter("email");
			String password=request.getParameter("password");
			if(username==null || email==null || password==null) {
				response.sendRedirect("login.jsp");
				return;
			}
			username=EscapeHTML.escapeHtml(username);
			email=EscapeHTML.escapeHtml(email);
			password=EscapeHTML.escapeHtml(password);
			if(utenteBC.getUser(username)!=null) {
				throw new IllegalArgumentException("Username già in uso");
			}
			Utente user=new Utente();
			user.setUsername(username);
			user.setEmail(email);
			user.setPassword(password);
			
			utenteBC.createUser(user);
			response.sendRedirect("login.jsp");
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

}
