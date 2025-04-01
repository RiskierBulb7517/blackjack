package com.seba.blackjack.bc.controller;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.seba.blackjack.bc.UtenteBC;
import com.seba.blackjack.security.EscapeHTML;

@WebServlet("/accedi")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = -708475732341222165L;
	private UtenteBC utenteBC;
	
	public void init(ServletConfig config) throws ServletException {
		utenteBC=UtenteBC.getUBC();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username=request.getParameter("username");
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		
		username=EscapeHTML.escapeHtml(username);
		email=EscapeHTML.escapeHtml(email);
		password=EscapeHTML.escapeHtml(password);
		
		HttpSession session=request.getSession();
		
		if(username!=null && password!=null) {
			
			try {
				
				if(!utenteBC.checkPass(username, password)) {
					response.sendRedirect("login.jsp");
					return;
				}
				session.setAttribute("username",username);
				response.sendRedirect("account.jsp");
			}catch(Exception e) {
				e.printStackTrace();
				throw new ServletException(e);
			}
			
		}else {
			response.sendRedirect("login.jsp");
		}
		
	}

}
